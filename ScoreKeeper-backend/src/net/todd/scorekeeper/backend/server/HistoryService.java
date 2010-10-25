package net.todd.scorekeeper.backend.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/history")
public class HistoryService {
	@GET
	@Produces("text/plain")
	public String list() {
		return "test";
	}
}
