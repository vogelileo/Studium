import java.util.Random;

public class Statistics {
	public static void main(String[] args) {
		double[] series = randomSeries(10000);
		// implement statistical analysis
		System.out.println(series.toString());
		//MIN
		double min = series[0];
		for (double s: series){
			if(s< min){
				min = s;
			}
		}
		//MAX
		double max = series[0];
		for(double s: series){
			if(s > max){
				max = s;
			}
		}
		//AVERAGE
		double sum = 0;
		for (double s:series){
			sum += s;
		}
		double average = sum/series.length;

		//VARIANT
		double var_sum = 0;
		for(double s:series){
			var_sum+=Math.pow(s-average,2);
		}
		double variant = var_sum / (series.length -1 );

		//StandardVariant
		double standard_variant = Math.sqrt(variant);
		System.out.println(" min "+ min+ " max "+ max+ " average "+ average + " varianz " + variant + " standard_variant " +standard_variant);
	}
	
	static double[] randomSeries(int amount) {
		double[] series = new double[amount];
		Random random = new Random(4711);
		for (int index = 0; index < amount; index++) {
			series[index] = random.nextDouble();
		}
		return series;
	}
}
