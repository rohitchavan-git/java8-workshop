package victor.training.java.stream.order.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

	public BigDecimal totalPrice;
	public LocalDate creationDate;


}
