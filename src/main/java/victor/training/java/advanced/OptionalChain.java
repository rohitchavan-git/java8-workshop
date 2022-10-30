package victor.training.java.advanced;

import java.util.Objects;
import java.util.Optional;

public class OptionalChain {
	static MyMapper mapper = new MyMapper();
   public static void main(String[] args) {
		Parcel parcel = new Parcel()
          .setDelivery(new Delivery(new Address().setContactPerson(new ContactPerson("John"))));

		DeliveryDto dto = mapper.convert(parcel);
      System.out.println(dto);
   }
}

class MyMapper {
   public DeliveryDto convert(Parcel parcel) {
      DeliveryDto dto = new DeliveryDto();
      dto.recipientPerson = getRecipientPerson(parcel);
      return dto;
   }

   public DeliveryDto pyramidOfNull(Parcel parcel) {
      DeliveryDto dto = new DeliveryDto();
      dto.recipientPerson = getRecipientPerson(parcel);
      return dto;
   }

   private static String getRecipientPerson(Parcel parcel) {
      return Optional.ofNullable(parcel)
              .flatMap(parcel1 -> Optional.ofNullable(parcel1.getDelivery()))
              .flatMap(delivery -> Optional.ofNullable(delivery.getAddress()))
              .flatMap(address -> Optional.ofNullable(address.getContactPerson()))
              .map(ContactPerson::getName)
              .map(String::toUpperCase)
              .orElse("<NOT SET>");
   }


}

class DeliveryDto {
	public String recipientPerson;
}

class Parcel {
   private Delivery delivery; // NULL until a delivery is scheduled

   public Delivery getDelivery() {
      return delivery;
   }
	public Parcel setDelivery(Delivery delivery) {
      this.delivery = delivery;
      return this;
   }
}


class Delivery {
   private Address address; // 'NOT NULL' IN DB

   public Delivery(Address address) {
      this.address = address;
   }

	public void setAddress(Address address) {
       Objects.requireNonNull(address);
       this.address = address; // TODO null safe
	}

	public Address getAddress() {
      return address;
   }
}

class Address {
   private ContactPerson contactPerson; // can be null

   public Address() {
   }

   public Address setContactPerson(ContactPerson contactPerson) {
      this.contactPerson = contactPerson;
      return this;
   }

   public ContactPerson getContactPerson() {
      return contactPerson;
   }
}

class ContactPerson {
   private final String name; // 'NOT NULL' in DB

   public ContactPerson(String name) {
      this.name = name;
   }

   public String getName() {
      return name;
   }
}
