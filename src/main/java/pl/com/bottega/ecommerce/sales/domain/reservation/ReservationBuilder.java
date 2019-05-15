package pl.com.bottega.ecommerce.sales.domain.reservation;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;

import java.util.Date;

public class ReservationBuilder {

    private Id reservationId;

    private Reservation.ReservationStatus reservationStatus;

    private ClientData clientData;

    private Date createDate;

    public ReservationBuilder reservationId(Id id) {
        this.reservationId = id;
        return this;
    }

    public ReservationBuilder reservationStatus(Reservation.ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
        return this;
    }

    public ReservationBuilder clientData(ClientData clientData) {
        this.clientData = clientData;
        return this;
    }

    public ReservationBuilder createDate(Date date) {
        this.createDate = date;
        return this;
    }

    public Reservation build() {
        return new Reservation(this.reservationId, this.reservationStatus, this.clientData, this.createDate);
    }

}
