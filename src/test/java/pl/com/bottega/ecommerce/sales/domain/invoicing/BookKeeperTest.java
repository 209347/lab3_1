package pl.com.bottega.ecommerce.sales.domain.invoicing;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mock;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductData;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductDataBuilder;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class BookKeeperTest {

    @Test
    public void requestingInvoiceWithOneItemShouldReturnInvoiceWithOneItem() {
        ClientData client = new ClientData(Id.generate(), "testClient1");
        InvoiceRequest invoiceRequest = new InvoiceRequest(client);

        ProductData productData = new ProductDataBuilder()
                .productId(Id.generate())
                .price(new Money(10))
                .name("productData")
                .type(ProductType.FOOD)
                .snapshotDate(new Date())
                .build();

        RequestItem requestItem = new RequestItem(productData, 1, new Money(10));
        invoiceRequest.add(requestItem);

        TaxPolicy taxPolicy = mock(TaxPolicy.class);
        when(taxPolicy.calculateTax(ProductType.FOOD, new Money(10)))
                .thenReturn(new Tax(new Money(1), "10%"));

        BookKeeper bookKeeper = new BookKeeper(new InvoiceFactory());
        Invoice result = bookKeeper.issuance(invoiceRequest, taxPolicy);

        assertThat(result.getItems().size(), Matchers.is(1));
    }

    @Test
    public void requestingInvoiceWithTwoItemsShouldCallCalculateTaxTwice() {
        ClientData client = new ClientData(Id.generate(), "testClient1");
        InvoiceRequest invoiceRequest = new InvoiceRequest(client);

        ProductData productData = new ProductDataBuilder()
                .productId(Id.generate())
                .price(new Money(10))
                .name("productData")
                .type(ProductType.FOOD)
                .snapshotDate(new Date())
                .build();

        RequestItem requestItem = new RequestItem(productData, 1, new Money(10));
        invoiceRequest.add(requestItem);
        invoiceRequest.add(requestItem);

        TaxPolicy taxPolicy = mock(TaxPolicy.class);
        when(taxPolicy.calculateTax(ProductType.FOOD, new Money(10)))
                .thenReturn(new Tax(new Money(1), "10%"));

        BookKeeper bookKeeper = new BookKeeper(new InvoiceFactory());
        Invoice invoiceResult = bookKeeper.issuance(invoiceRequest, taxPolicy);

        verify(taxPolicy, times(2)).calculateTax(ProductType.FOOD, new Money(10));
    }

    @Test
    public void requestingInvoiceWithThreeItemsShouldReturnInvoiceWithThreeItems() {
        ClientData client = new ClientData(Id.generate(), "testClient1");
        InvoiceRequest invoiceRequest = new InvoiceRequest(client);

        ProductData productData = new ProductDataBuilder()
                .productId(Id.generate())
                .price(new Money(10))
                .name("productData")
                .type(ProductType.FOOD)
                .snapshotDate(new Date())
                .build();

        RequestItem requestItem = new RequestItem(productData, 1, new Money(10));
        invoiceRequest.add(requestItem);
        invoiceRequest.add(requestItem);
        invoiceRequest.add(requestItem);

        TaxPolicy taxPolicy = mock(TaxPolicy.class);
        when(taxPolicy.calculateTax(ProductType.FOOD, new Money(10)))
                .thenReturn(new Tax(new Money(1), "10%"));

        BookKeeper bookKeeper = new BookKeeper(new InvoiceFactory());
        Invoice result = bookKeeper.issuance(invoiceRequest, taxPolicy);

        assertThat(result.getItems().size(), Matchers.is(3));
    }

    @Test
    public void requestingEmptyInvoiceShouldNotCallCalculateTax() {
        ClientData client = new ClientData(Id.generate(), "testClient1");
        InvoiceRequest invoiceRequest = new InvoiceRequest(client);

        ProductData productData = new ProductDataBuilder()
                .productId(Id.generate())
                .price(new Money(10))
                .name("productData")
                .type(ProductType.FOOD)
                .snapshotDate(new Date())
                .build();

        TaxPolicy taxPolicy = mock(TaxPolicy.class);
        when(taxPolicy.calculateTax(ProductType.FOOD, new Money(10)))
                .thenReturn(new Tax(new Money(1), "10%"));

        BookKeeper bookKeeper = new BookKeeper(new InvoiceFactory());
        Invoice invoiceResult = bookKeeper.issuance(invoiceRequest, taxPolicy);

        verify(taxPolicy, times(0)).calculateTax(any(), any());
    }

    @Test
    public void invoiceShouldReturnCorrectClientData(){
        Id id = new Id("5");
        ClientData client = new ClientData(id, "testClient1");
        InvoiceRequest invoiceRequest = new InvoiceRequest(client);

        ProductData productData = new ProductDataBuilder()
                .productId(Id.generate())
                .price(new Money(10))
                .name("productData")
                .type(ProductType.FOOD)
                .snapshotDate(new Date())
                .build();

        RequestItem requestItem = new RequestItem(productData, 1, new Money(10));
        invoiceRequest.add(requestItem);
        invoiceRequest.add(requestItem);
        invoiceRequest.add(requestItem);

        TaxPolicy taxPolicy = mock(TaxPolicy.class);
        when(taxPolicy.calculateTax(ProductType.FOOD, new Money(10)))
                .thenReturn(new Tax(new Money(1), "10%"));

        BookKeeper bookKeeper = new BookKeeper(new InvoiceFactory());
        Invoice result = bookKeeper.issuance(invoiceRequest, taxPolicy);

        assertThat(result.getClient().getName(), Matchers.is("testClient1"));
        assertThat(result.getClient().getAggregateId().getId(), Matchers.is("5"));
    }

    @Test
    public void requestingInvoiceWithOneItemShouldCallCalculateTaxOnce() {
        ClientData client = new ClientData(Id.generate(), "testClient1");
        InvoiceRequest invoiceRequest = new InvoiceRequest(client);

        ProductData productData = new ProductDataBuilder()
                .productId(Id.generate())
                .price(new Money(10))
                .name("productData")
                .type(ProductType.FOOD)
                .snapshotDate(new Date())
                .build();

        RequestItem requestItem = new RequestItem(productData, 1, new Money(10));
        invoiceRequest.add(requestItem);

        TaxPolicy taxPolicy = mock(TaxPolicy.class);
        when(taxPolicy.calculateTax(ProductType.FOOD, new Money(10)))
                .thenReturn(new Tax(new Money(1), "10%"));

        BookKeeper bookKeeper = new BookKeeper(new InvoiceFactory());
        Invoice invoiceResult = bookKeeper.issuance(invoiceRequest, taxPolicy);

        verify(taxPolicy, times(1)).calculateTax(ProductType.FOOD, new Money(10));
    }
}
