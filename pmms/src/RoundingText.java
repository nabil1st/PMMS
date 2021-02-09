import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


public class RoundingText {
	public static void main(String[] args) {
		BigDecimal tax = new BigDecimal("8.25");
		List<BigDecimal> list = new ArrayList<BigDecimal>();
		list.add(new BigDecimal("0.84"));
		list.add(new BigDecimal("5.97"));
		list.add(new BigDecimal("8.97"));
		list.add(new BigDecimal("8.97"));
		list.add(new BigDecimal("1.27"));
		list.add(new BigDecimal("1.27"));
		
		BigDecimal total = new BigDecimal(0);
		
		for (BigDecimal amount : list) {
			BigDecimal taxDue = amount.multiply(tax).divide(new BigDecimal("100.00"));
			total = total.add(amount);
			total = total.add(taxDue);
		}
		
		System.out.println("Total = " + total.divide(new BigDecimal(1), 2, RoundingMode.DOWN).toString());
	}
}
