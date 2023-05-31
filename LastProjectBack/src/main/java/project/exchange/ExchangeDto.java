package project.exchange;

import lombok.Data;

@Data
public class ExchangeDto {

	private int exchangeIdx;
	private String exchangeNationShort;
	private String exchangeNation;
	private double exchangeRate;
}
