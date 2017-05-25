package test;

public class Test {
	public static void main(String[] args) {
		int sum = 50*1000;
		
		double money1 = 10;
		double money2 = 0;
		double money3 = 0;
		double money4 = 0;
		if(sum > 50){
			money2 = 50*0.5;
		}
		
		if(sum > 1000){
			money3 = (1000-50)*0.2;
		}
		
		if(sum > 10000){
			money4 = (10000-1000)*0.05;
		}
		
		System.out.println("基本收费："+money1+"元");
		System.out.println("50M收费："+money2+"元");
		System.out.println("1KM收费："+money3+"元");
		System.out.println("10G收费："+money4+"元");
		System.out.println("总计收费："+(money1+money2+money3+money4)+"元");
		
		
	}
}
