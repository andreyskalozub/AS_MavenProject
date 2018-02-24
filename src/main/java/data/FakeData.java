package data;

import com.github.javafaker.Faker;

public class FakeData extends Faker {

	Faker faker = new Faker();

	public String firstName = faker.name().firstName();
	public String lastName = faker.name().lastName();
	public String phoneNumber = faker.code().ean13();
	public String address1 = faker.address().streetAddress();
	public String city = faker.address().cityName();
	public String postalCode = faker.address().zipCode();
	public String fullName = faker.name().fullName();
	public String streetAddress = faker.address().streetAddress();
	public String county = faker.address().stateAbbr();
	public String nickname = faker.name().username();
}
