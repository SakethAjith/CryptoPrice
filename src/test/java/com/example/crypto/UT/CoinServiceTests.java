package com.example.crypto.UT;

import com.example.crypto.responseObjects.Coin;
import com.example.crypto.service.CoinService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class CoinServiceTests {

	@Autowired
	CoinService coinService;

	@Test
	void contextLoads() {
	}

	@Test
	void getCoinTest() throws IOException {
		String testResponse="{\n" +
				"  \"bitcoin\": {\n" +
				"    \"usd\": 26400\n" +
				"  }\n" +
				"}";
		coinService.getCoin("bitcoin",testResponse);
	}

	@Test
	void getCoinsTest() throws IOException, InterruptedException {
		List<Coin> coins=new ArrayList<>();
		coins.add(new Coin("bitcoin"));
		coins.add(new Coin("ethereum"));
		coinService.getCoinsList(coins);
	}


//	@Test
//	void getCoinBoardtest() throws IOException, InterruptedException {
//		controller.getCoinBoard();
//	}
//
//	@Test
//	void checkDbConnectionTest(){
//		Assert.assertEquals(controller.checkDbConnection(),true);
//	}

}
