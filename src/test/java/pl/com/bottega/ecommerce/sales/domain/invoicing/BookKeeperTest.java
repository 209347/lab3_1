package pl.com.bottega.ecommerce.sales.domain.invoicing;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mock;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductData;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BookKeeperTest {

    @Test public void requestingInvoiceWithOneItemShouldReturnInvoiceWithOneItem() {
        ClientData client = new ClientData(Id.generate(), "testClient1");
        InvoiceRequest invoiceRequest = new InvoiceRequest(client);

        ProductData productData = mock(ProductData.class);
        when(productData.getType()).thenReturn(ProductType.FOOD);

        RequestItem requestItem = new RequestItem(productData, 1, new Money(10));
        invoiceRequest.add(requestItem);

        BookKeeper bookKeeper = new BookKeeper(new InvoiceFactory());

        TaxPolicy taxPolicy = mock(TaxPolicy.class);
        when(taxPolicy.calculateTax(ProductType.FOOD, new Money(10))).thenReturn(new Tax(new Money(1), "10%"));

        Invoice result = bookKeeper.issuance(invoiceRequest, taxPolicy);

        assertThat(result.getItems().size(), Matchers.is(1));
    }
}