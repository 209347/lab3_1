package pl.com.bottega.ecommerce.sales.application.api.handler;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.application.api.command.AddProductCommand;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.Product;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductRepository;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sales.domain.reservation.Reservation;
import pl.com.bottega.ecommerce.sales.domain.reservation.ReservationRepository;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AddProductCommandHandlerTest {

    @Test
    public void handleShouldReturnNull() {
        AddProductCommandHandler addProductCommandHandler = new AddProductCommandHandler();
        AddProductCommand addProductCommand = new AddProductCommand(Id.generate(), Id.generate(), 1);

        Reservation reservation = mock(Reservation.class);
        ReservationRepository reservationRepository = mock(ReservationRepository.class);

        Whitebox.setInternalState(addProductCommandHandler, "reservationRepository", reservationRepository);
        when(reservationRepository.load(any(Id.class))).thenReturn(reservation);

        ProductRepository productRepository = mock(ProductRepository.class);

        Whitebox.setInternalState(addProductCommandHandler, "productRepository", productRepository);
        Product product = new Product(Id.generate(), new Money(10), "Product1", ProductType.FOOD);
        when(productRepository.load(any(Id.class))).thenReturn(product);

        assertThat(addProductCommandHandler.handle(addProductCommand), Matchers.equalTo(null));
    }
}