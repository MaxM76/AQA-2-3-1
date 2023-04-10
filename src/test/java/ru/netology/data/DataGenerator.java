package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class DataGenerator {
    private DataGenerator() {
    }

    public static String generateDate(int daysShift) {
        return LocalDate.now().plusDays(daysShift).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            Faker faker = new Faker(new Locale(locale));
            return new UserInfo(
                    faker.address().cityName(),
                    faker.name().fullName(),
                    faker.phoneNumber().phoneNumber()
            );
        }

    }

    @Value
    @AllArgsConstructor
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}
