import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
public class SnowmanGameModelTest {
	
	private SnowmanGameModel model; 
	
	@Before
	public void setUp() throws Exception {
		model = new SnowmanGameModel();
	}
	
	@Test
	public void getGuessesWorks() {
		model.guesses = 4;
		assertEquals(model.getGuesses(), 4);
	}
	
	@Test
	public void getLengthWorks() {
		model.length = 4;
		assertEquals(model.getLength(), 4);
	}
	
	@Test
	public void DictionaryFillsUp(){ 
		model.initializeGameDictionary();
		assertFalse(model.dictionary.isEmpty());
	}
	
	@Test
	public void GameIsRunning(){
		model.guesses = 4;
		model.run();
		assertTrue(model.guesses < 4);
	}
}
