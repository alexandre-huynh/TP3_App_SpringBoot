package TestJpa.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    private static List<Person> persons = new ArrayList<Person>();

    static {
        persons.add(new Person("Bill", "Gates"));
        persons.add(new Person("Steve", "Jobs"));
    }

    // Inject via application.properties
    @Value("${welcome:message}")
    private String message;

    @Value("${error:message}")
    private String errorMessage;

    // en gros quand on tape / ou /index dans l'URL, renvoie vers la page index
    // ne reconnait pas le reste
    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model) {

        model.addAttribute("message", message);

        return "issues/index";
    }

    // si url /personlist affiche la liste des personnes mais c'est logique
    @RequestMapping(value = { "/personList" }, method = RequestMethod.GET)
    public String personList(Model model) {
        Search search = new Search();

        model.addAttribute("persons", persons);
        model.addAttribute("search", search); // doit avoir le même nom que sur le formulaire html personlist.html
        return "issues/personList";
    }

    // ============== MON CODE POUR SEARCH =========================
    @RequestMapping(value = { "/searchPerson" }, method = RequestMethod.POST)
    public String searchPerson(Model model, @ModelAttribute("personForm") Search search) {

        // on cherche via firstname
        String firstName = search.getFirstName();

        // nouvelle liste avec les personnes matchant ce nom
        List<Person> persons_match = new ArrayList<Person>();

        // alternative si prénom est un identifiant, donc unique = il y en qu'1 seul
        //Person persons_match = null;

        // si firstname searché n'est pas vide
        if (firstName != null && firstName.length() > 0) {
            // on parcours la liste des personnes existantes
            for (int i = 0; i < persons.size(); i++) {

                // si on trouve une personne avec même firstName, retourner
                if ( persons.get(i).getFirstName().equals(firstName) ){

                    // on rajoute à la liste des personnes qui matchent le firstname
                    persons_match.add(persons.get(i));

                    // alternative si on veut retourner qu'un seul identifiant
                    //persons_match = persons.get(i);

                } // fin condition if

            } // fin parcours liste personnes

            model.addAttribute("persons", persons_match);
            model.addAttribute("search", search);
            return "issues/personList";

        } // fin boucle

        model.addAttribute("errorMessage", errorMessage);
        return "issues/personList";
    }
    // ===================================================

    @RequestMapping(value = { "/addPerson" }, method = RequestMethod.GET)
    public String showAddPersonPage(Model model) {

        Person person = new Person(); //
        model.addAttribute("personForm", person); //

        return "issues/addPerson";
    }

    @RequestMapping(value = { "/addPerson" }, method = RequestMethod.POST)
    public String savePerson(Model model, @ModelAttribute("personForm") Person person) {

        String firstName = person.getFirstName();
        String lastName = person.getLastName();

        if (firstName != null && firstName.length() > 0 //
                && lastName != null && lastName.length() > 0) {
            Person newPerson = new Person(firstName, lastName);
            persons.add(newPerson);

            return "redirect:/personList";
        }

        model.addAttribute("errorMessage", errorMessage);
        return "issues/addPerson";
    }


}
