package br.com.homebroker.controller.active;

import static br.com.caelum.vraptor.view.Results.json;

import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.homebroker.model.Active;
import br.com.homebroker.service.ActiveService;
import br.com.homebroker.service.Service;
import br.com.homebroker.util.exceptions.NothingFoundException;

@Resource
@Path("/active")
public class ActiveController {
	
	private final Result result;
	
	private final Service<Active> service;
	
	public ActiveController(Result result, ActiveService service){
		this.result = result;
		this.service = service;
	}

	@Get("")
	public void list() {
		try{
			List<Active> actives = this.service.listAll();
			this.result.use(json()).withoutRoot().from(actives).serialize();			
		}catch(NothingFoundException nothingFound){
			this.result.use(json()).from(nothingFound.getMessage(), "message").serialize();
		}
	}

	@Path(value="{code}", priority=Path.LOWEST)
	public void search(String code) {
		try{
			Active active = this.service.search(code);
			this.result.use(json()).withoutRoot().from(active).serialize();
		}catch(NothingFoundException nothingFound){
			this.result.use(json()).from(nothingFound.getMessage(), "message").serialize();
		}
	}

	@Get("{id}")
	public void search(Long id){
		try{
			System.out.println("here");
			Active active = this.service.search(id);
			this.result.use(json()).withoutRoot().from(active).serialize();
		}catch (NothingFoundException e) {
			this.result.use(json()).from(e.getMessage(), "message").serialize();
		}
	}
	
	@Post("")
	public void save(Active active) {
		if(this.service.save(active))
			this.result.use(json()).from("Ativo inserido com sucesso", "message").serialize();
		else
			this.result.use(json()).from("Ativo não inserido. Verifique se os campos foram informados corretamente.", "message").serialize();
	}
}
