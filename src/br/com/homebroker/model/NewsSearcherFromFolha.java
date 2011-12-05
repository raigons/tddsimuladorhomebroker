package br.com.homebroker.model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.web.client.RestTemplate;

import br.com.caelum.vraptor.ioc.Component;
import br.com.homebroker.model.util.DateConvertor;

@Component
public class NewsSearcherFromFolha extends NewsSearcher {

	public NewsSearcherFromFolha(RestTemplate restTemplate, DateConvertor dateConvertor) {
		super(restTemplate, dateConvertor);
	}

	protected List<News> list(String news){
		String[] arrayNews = news.split("<!--RESULTSET-->");
		String allNews = arrayNews[1];
		arrayNews = allNews.split("<!--/RESULTSET-->");
		allNews = arrayNews[0];
		allNews = allNews.substring(0, allNews.indexOf("<p>Mais resultados:"));
		convertNews(allNews);
		return list;
	}

	private void convertNews(String allNewsAsString){
		int x = 1;		
		while(thereIsANews(allNewsAsString)){
			News news = new News();
			news.setLink(getLinkOfANews(allNewsAsString));
			news.setHeadline(getHeadline(allNewsAsString));
			news.setDate(getDate(news.getHeadline()));
			getContentText(allNewsAsString);
			news.setText(getContentText(allNewsAsString));
			list.add(news);
			String allNewsAsStringNew = removeLineRead(allNewsAsString, x, news);
			allNewsAsString = "";
			allNewsAsString = allNewsAsStringNew;
			x++;
		}	
	}
	
	private String removeLineRead(String allNewsAsString, int index, News news){
		String htmlToRemove = "<b>"+index+".</b> ";
		//htmlToRemove += "<a href=\""+news.getLink()+"\">";
		//htmlToRemove += news.getHeadline()+"</a><br>";
		String stringToRemove;
		int beginIndex = allNewsAsString.indexOf(htmlToRemove);
		int endIndex = allNewsAsString.indexOf(".shtml</span>");
		stringToRemove = allNewsAsString.substring(beginIndex, endIndex+13);
		allNewsAsString = allNewsAsString.replace(stringToRemove, "");
		return allNewsAsString;
	}
	
	private String getLinkOfANews(String htmlNews){
		String begin = "<a href=\"";
		String end = ".shtml\">";
		int beginIndex = htmlNews.indexOf(begin) + begin.length();
		int endIndex = htmlNews.indexOf(end);
		String link = htmlNews.substring(beginIndex, endIndex)+".shtml";
		return link;
	}
	
	private String getHeadline(String htmlNews){
		String begin = ".shtml\">";
		String end = "</a><br>";		
		int beginIndex = htmlNews.indexOf(begin)+begin.length();
		int endIndex = htmlNews.indexOf(end);
		String headline = htmlNews.substring(beginIndex, endIndex);
		return headline;
	}

	private String getContentText(String htmlNews){
		String begin = "</a><br>";//headline's end
		//String end = "<br> <span";
		String end = "... <br>";
		int beginIndex = htmlNews.indexOf(begin) + begin.length();
		int endIndex = htmlNews.indexOf(end)+4;
		System.out.println(beginIndex+", "+endIndex);
		System.out.println(htmlNews);
		String contentText = htmlNews.substring(beginIndex, endIndex);
		return contentText.trim();
	}
	
	private Date getDate(String newsHeadline){
		dateConvertor = new DateConvertor();
		Pattern pattern = Pattern.compile(dateConvertor.getOnlyDatePatternWithSlashes());
		Matcher matcher = pattern.matcher(newsHeadline);
		if(matcher.find()){
			String date = matcher.group();
			return dateConvertor.convertStringToDate(date);
		}
		return null;
	}
	
	private boolean thereIsANews(String html){
		return html.indexOf("</b> <a href=\"http://www1.folha.uol.com.br") != -1;
	}

	@Override
	protected String getUrl() {
		return "http://search.folha.com.br/search?q={q}&sr={sr}";
	}

	@Override
	protected Map<String, String> getParams() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("q", "bovespa");
		params.put("sr", String.valueOf(1));
		return params;
	}

}
