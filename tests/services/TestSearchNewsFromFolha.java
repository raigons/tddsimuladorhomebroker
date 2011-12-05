package services;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import br.com.homebroker.model.News;
import br.com.homebroker.model.NewsSearcher;
import br.com.homebroker.model.NewsSearcherFromFolha;
import br.com.homebroker.model.util.DateConvertor;

public class TestSearchNewsFromFolha {
	
	@Mock
	private RestTemplate restTemplate;
	
	private NewsSearcher searcher; 
	
	private List<News> list;
	
	@Before
	public void init() throws RestClientException, IOException{
		MockitoAnnotations.initMocks(this);
		Map<String, String> params = new HashMap<String, String>();
		params.put("q", "bovespa");
		params.put("sr", String.valueOf(1));		
		Mockito.when(restTemplate.getForObject("http://search.folha.com.br/search?q={q}&sr={sr}", String.class, params)).thenReturn(stringNewsFromFrolha());
		searcher = new NewsSearcherFromFolha(restTemplate, new DateConvertor());
		list = searcher.getNews();
	}
	
	@Test
	public void testGetTheFirstNews(){
		assertEquals("http://www1.folha.uol.com.br/mercado/990889-horario-de-verao-faz-bmfbovespa-abrir-mais-tarde.shtml", list.get(0).getLink());
		assertEquals("Folha.com - Mercado - Horário de verão faz BM&FBovespa abrir mais tarde - 17/10/2011", list.get(0).getHeadline());
		assertEquals("... horários de funcionamento do mercado de capitais brasileiro a partir desta segunda-feira. O pregão regular da <b>Bovespa</b> (Bolsa de Valores de São Paulo) passa a funcionar das 11h (hora  ...", list.get(0).getText());
		assertDateEquals(17, 9, 0);
	}
	
	@Test
	public void testGetSecondNews(){
		assertEquals("http://www1.folha.uol.com.br/mercado/990959-conselho-da-vale-aprova-remuneracao-minima-aos-acionistas.shtml", list.get(1).getLink());
		assertEquals("Folha.com - Mercado - Conselho da Vale aprova remuneração mínima aos acionistas - 14/10/2011", list.get(1).getHeadline());
		assertEquals("... remuneração ao acionista. A \"record date\" para as ações de emissão da Vale negociadas na BM&F <b>Bovespa</b> é o dia 14 de outubro de 2011, enquanto para os ADRs negociados na Bolsa de  ...", list.get(1).getText());
		assertDateEquals(14, 9, 1);
	}
	
	@Test
	public void testGetTenthNews(){
		assertEquals("http://www1.folha.uol.com.br/mercado/990161-em-setimo-dia-de-baixa-dolar-fecha-a-r-175-bovespa-sobe-14.shtml", list.get(9).getLink());
		assertEquals("Folha.com - Mercado - Em sétimo dia de baixa, dólar fecha a R$ 1,75; <b>Bovespa</b> sobe 1,4% - 13/10/2011", list.get(9).getHeadline());
		assertEquals("... 13/10/2011 16h56 Em sétimo dia de baixa, dólar fecha a R$ 1,75; <b>Bovespa</b> sobe 1,4% DE SÃO PAULO Atualizado às 19h28 . Em seu sétimo dia consecutivo de baixa, a taxa de câmbio  ... por R$ 1,860 (baixa de 0,53%) e comprado por R$ 1,680 nas casas de câmbio paulistas. No fechamento, a <b>Bovespa</b> apresentou alta de 1,42%, aos 54.601 pontos. O giro financeiro foi  ...", list.get(9).getText());
		assertDateEquals(13, 9, 9);
	}
	
	@Test
	public void testGetSeventhFifthNews(){
		assertEquals("http://www1.folha.uol.com.br/mercado/988240-bovespa-ganha-150-na-abertura-dolar-vale-r-174.shtml", list.get(24).getLink());
		assertEquals("Folha.com - Mercado - <b>Bovespa</b> ganha 1,50% na abertura; dólar vale R$ 1,74 - 10/10/2011", list.get(24).getHeadline());
		assertEquals("... 10/10/2011 10h19 <b>Bovespa</b> ganha 1,50% na abertura; dólar vale R$ 1,74 DE SÃO PAULO O mercado brasileiro de ações inicia os negócios desta segunda-feira em terreno positivo, ... Ibovespa, o termômetro dos negócios da Bolsa paulista, avança 1,50%, aos 52.010 pontos. Na sexta-feira, a <b>Bovespa</b> fechou em queda de 2%. O dólar comercial é negociado por R$ 1, ...", list.get(24).getText());
		assertDateEquals(10, 9, 24);
	}

	private void assertDateEquals(int day, int month, int newsPosition){
		Calendar calendar = Calendar.getInstance();
		calendar.set(2011, month, day, 0,0,0);
		Calendar dateFromNews = Calendar.getInstance();
		dateFromNews.setTime(list.get(newsPosition).getDate());
		assertEquals(calendar.get(Calendar.DATE), dateFromNews.get(Calendar.DATE));
		assertEquals(calendar.get(Calendar.MONTH), dateFromNews.get(Calendar.MONTH));
		assertEquals(calendar.get(Calendar.YEAR), dateFromNews.get(Calendar.YEAR));			
	}
	
	private String stringNewsFromFrolha() throws IOException{
		InputStream in = new FileInputStream("tests/services/padrao_noticias_folha_html_completo.txt");
		InputStreamReader inputReader = new InputStreamReader(in);
		BufferedReader reader = new BufferedReader(inputReader);
		String line;
		String html = "";
		while((line = reader.readLine()) != null){
			html += line;
		}
		return html;
	}
}
