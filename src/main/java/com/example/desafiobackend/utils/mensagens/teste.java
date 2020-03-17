package com.example.desafiobackend.utils.mensagens;

public class teste {

	public static void main(String[] args) {
		String data= "2018-03-14T08:00:00";
		String data2= "2018-03-14T08:00:00Z";
		String separados[] = data.split("T");
		java.time.LocalDate local= java.time.LocalDate.parse(separados[0]);
		java.time.LocalDateTime Ldt = java.time.LocalDateTime.parse(data);
		java.time.LocalTime localTime= java.time.LocalTime.parse(separados[1]);
		
		System.out.println(local.getDayOfMonth()+"/"+local.getMonthValue()+"/"+local.getYear());
		System.out.println(localTime.getHour()+":"+localTime.getMinute()+":"+localTime.getSecond());
		
	}

}
