package net.todd.scorekeeper.backend.server;

import java.io.StringWriter;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


import org.codehaus.jackson.map.ObjectMapper;

@Path("/history")
public class HistoryService {
	@GET
	@Produces("text/plain")
	public String list() {
		History history = new History();
		history.setName("your mom");
		
		ObjectMapper mapper = new ObjectMapper();
		StringWriter out = new StringWriter();
		try {
			mapper.writeValue(out, history);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return out.toString();
	}
}
