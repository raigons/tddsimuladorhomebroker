package br.com.homebroker.controller;

import static br.com.caelum.vraptor.view.Results.json;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.homebroker.model.Active;
import br.com.homebroker.model.News;
import br.com.homebroker.model.NewsSearcher;
import br.com.homebroker.model.util.DateConvertor;
import br.com.homebroker.service.NewsService;
import br.com.homebroker.service.Service;
import br.com.homebroker.util.exceptions.NothingFoundException;

@Resource
@Path("/news")
public class NewsController {

	private final Service<News> service;
	private final NewsSearcher searcherService;
	private final DateConvertor convetor;
	private final Result result;
	
	public NewsController(NewsService service, NewsSearcher searcherService, DateConvertor convertor, Result result) {
		this.service = service;
		this.searcherService = searcherService;
		this.convetor = convertor;
		this.result = result;
	}

	@Get("{id}")
	public void search(Long id) {
		try{			
			News news = this.service.search(id);
			this.result.use(json()).withoutRoot().from(news).include("active").exclude("date","@resolves-to").serialize();
		}catch(NothingFoundException exception){
			this.result.use(json()).from(exception.getMessage(), "message").serialize();
		}
	}

	@Get
	@Path(value="{active.code}", priority=Path.LOW)
	public void search(Active active) {
		try{
			List<News> list = this.service.searchByActive(active);
			this.result.use(json()).withoutRoot().from(list).include("active").exclude("date").serialize();			
		}catch(NothingFoundException exception){
			this.result.use(json()).from(exception.getMessage(), "message").serialize();
		}
	}

	@Get("{active.code}/{begin}/{end}.json")
	public void search(Active active, String begin, String end) {
		Date beginDate = convetor.convertStringToDate(begin);
		Date endDate = convetor.convertStringToDate(end);
		if(beginDate != null && endDate != null){
			try{
				List<News> list = this.service.search(active, beginDate, endDate);
				this.result.use(json()).withoutRoot().from(list).include("active").exclude("date","@resolves-to","@class").serialize();
			}catch(NothingFoundException exception){
				this.result.use(json()).from(exception.getMessage(), "message").serialize();
			}
		}else{
			this.result.use(json()).from("As datas não estão em um formato correto", "message").serialize();
		}
	}

	@Path("external")
	public synchronized void searchExternalNews() {
		Iterator<News> allNews = this.searcherService.getNews().iterator();
		listFoundExternalNews(allNews);
	}
	
	private void listFoundExternalNews(Iterator<News> allNews){
		System.out.println("\nListando notícias encontradas\n");
		while(allNews.hasNext()){
			News news = allNews.next();
			System.out.println("Link = "+news.getLink());
			System.out.println("Texto = "+news.getText());
			if(news.getDate() != null){					
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(news.getDate());
				System.out.println("Date = "+calendar.get(Calendar.DAY_OF_MONTH)+"/"+calendar.get(Calendar.MONTH)+"/"+calendar.get(Calendar.YEAR));
			}
			System.out.println("Manchete = "+news.getHeadline());
			System.out.println("\n-----------------\n");
		}
	}

}

