package theMovieAdvisorsRSClient;

import java.util.ArrayList;

import javax.ws.rs.core.Response;
import org.apache.cxf.jaxrs.client.WebClient;
import event.management.web.data.Artist;
import event.management.web.data.Event;

/**
 * Classe permettant le test du Service Web.
 * 
 * @author Salma BENCHELKHA & Mouncif LEKMITI
 * @version 1.0
 *
 */
public class Test {	
	private static String webServiceUrl = "http://localhost:8080/theMovieAdvisorsRS/api/events";
	
	public static void main(String[] args) {
		
		Artist selenaGomez = new Artist(1, "Selena Gomez", "Mexicaine");
		Artist davidGuetta = new Artist(2, "David Guetta", "Fran�ais");
		Artist martinGarrix = new Artist(3, "Martin Guarrix", "N�erlandais");

		Event coachella = new Event(add("Coachella", "21-04-2021", "Californie",selenaGomez), "Coachella", "21-04-2021", "Californie", selenaGomez);		
		System.out.println(coachella);
		
		Event tomorrowland = new Event(add("Tomorrowland", "27-08-2021", "Belgique",davidGuetta), "Tomorrowland", "27-08-2021", "Belgique", davidGuetta);
		System.out.println(tomorrowland);
		
		Event lollapalooza = new Event(add("Lollapalooza", "30-07-2021", "Chicago",martinGarrix), "Lollapalooza", "30-07-2021", "Chicago", martinGarrix);
		System.out.println(lollapalooza);
		
		getByArea("Californie");
		//getByArtist(davidGuetta.getId(), davidGuetta);
		//getAll();
	}
	
	/**
	 * M�thode permettant d'ajouter un artiste � un �v�nement.
	 *  
	 * @param name
	 * @param date
	 * @param area
	 * @param artist
	 * @return L'id de l'artiste.
	 *
	 */
	private static Integer add(String name, String date, String area,Artist artist) {
		System.out.print("Ajout de l'�venement : " + name);
		WebClient c = WebClient.create(webServiceUrl);
		Event event = new Event(name, date, area,artist);
		Response r = c.post(event);
		if(r.getStatus() == 400) {
			System.out.println("Oops!");
			return null;
		}
		String uri = r.getHeaderString("Content-Location");
		System.out.println("Exit with status "+r.getStatus()+".");
		System.out.println("OK.");
		return Integer.parseInt(uri.substring(uri.lastIndexOf('/') + 1));
	}
	
	/**
	 * M�thode permettant de r�cup�rer les �v�nements � partir d'une r�gion.
	 *  
	 * @param area
	 * @return Une response.
	 *
	 */
	private static Event[] getByArea(String area) {
		System.out.println("R�cup�ration de tout les �v�nements de la r�gion : "+area);
		WebClient c = WebClient.create(webServiceUrl).path("areas/"+area);
		ArrayList<Event> l = (ArrayList<Event>) c.getCollection(Event.class);
	    for(Event e : l) {
	      System.out.println(e.toString());
	    }
	    //System.out.println("Exit with status "+c.get().getStatus()+".");
	    System.out.println("OK.");
	    return l.toArray(new Event[l.size()]);
	  }
	
	/**
	 * M�thode permettant de r�cup�rer les �v�nements � partir d'un artiste.
	 *  
	 * @param id
	 * @param artiste
	 * @return Une response.
	 *
	 */
	private static Event[] getByArtist(int id, Artist artist) {
		System.out.println("R�cup�ration de tout les �v�nements de l'artiste : "+artist.getName());
		WebClient c = WebClient.create(webServiceUrl).path(id);
		//System.out.println(webServiceUrl+"/"+artist.getId());
		ArrayList<Event> l = (ArrayList<Event>) c.getCollection(Event.class);
	    for(Event e : l) {
	      System.out.println(e.toString());
	    }
	    //System.out.println("Exit with status "+c.get().getStatus()+".");
	    System.out.println("OK.");
	    return l.toArray(new Event[l.size()]);
	  }	
	
	/**
	 * M�thode permettant de r�cup�rer tout les �v�nements.
	 *  
	 * @return Une response.
	 *
	 */
	private static Event[] getAll() {
		System.out.println("R�cup�ration de tout les �v�nements.");
	    WebClient c = WebClient.create(webServiceUrl);
	    ArrayList<Event> l = (ArrayList<Event>) c.getCollection(Event.class);
	    for(Event e : l) {
	      System.out.println(e.toString());
	    }
	    System.out.println("OK.");
	    return l.toArray(new Event[l.size()]);
	  }
	
}