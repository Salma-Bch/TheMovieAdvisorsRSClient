package theMovieAdvisorsRSClient;

import java.util.ArrayList;

import javax.ws.rs.core.Response;
import org.apache.cxf.jaxrs.client.WebClient;
import event.management.web.data.Artist;
import event.management.web.data.Event;

public class Test {
	private static String webServiceUrl = "http://localhost:8080/theMovieAdvisorsRS/api/events";
	public static void main(String[] args) {
		Artist artist = new Artist(1, "Salmax", "Marocaine");
		Event event = new Event(add("Anniversaire", "10-10-2021", "France",artist), "Anniversaire", "10-10-2021", "France",artist);		
		System.out.println(event);
		Event event2 = new Event(add("Anniversaire2", "08-07-2021", "France",artist), "Anniversaire2", "08-07-2021", "France",artist);
		System.out.println(event2);
		Event event3 = new Event(add("Anniversaire3", "08-11-2021", "Angleterre",artist), "Anniversaire3", "08-11-2021", "Angleterre",artist);
		System.out.println(event3);
		//add(event.getId(), event.getName(), event.getDate(), event.getArea(), event.getArtist());
		//getByArea("France");
		//getByArtist(artist.getId(), artist);
		//delete(event.getId());
		getAll();
	}
	
	private static Integer add(String name, String date, String area,Artist artist) {
		System.out.print("Ajout de l'évenement : " + name + "... ");
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
		
	private static Event[] getByArea(String area) {
		System.out.println("Getting all event of area "+area+"...");
		WebClient c = WebClient.create(webServiceUrl).path("areas/"+area);
		ArrayList<Event> l = (ArrayList<Event>) c.getCollection(Event.class);
	    for(Event e : l) {
	      System.out.println(e.toString());
	    }
	    //System.out.println("Exit with status "+c.get().getStatus()+".");
	    System.out.println("OK.");
	    return l.toArray(new Event[l.size()]);
	  }
	
	private static Event[] getByArtist(int id, Artist artist) {
		System.out.println("Getting all events of artist "+artist.getName()+"...");
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
	
	private static Event[] getAll() {
		System.out.println("Getting all events...");
	    WebClient c = WebClient.create(webServiceUrl);
	    ArrayList<Event> l = (ArrayList<Event>) c.getCollection(Event.class);
	    for(Event e : l) {
	      System.out.println(e.toString());
	    }
	    System.out.println("OK.");
	    return l.toArray(new Event[l.size()]);
	  }
	
}