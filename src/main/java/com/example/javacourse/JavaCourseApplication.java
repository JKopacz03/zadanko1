package com.example.javacourse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavaCourseApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaCourseApplication.class, args);
	}

	// aplikacja do osblugi kursow/nauki javy

	// "architektura" - controller/service/repo
	//Model:
	// Student - long id, name, lastName, email, grade, lessons (many to many), registrationDate
	// Teacher - id, name, lastName, email, grade, rate, lessons (oneToMany)
	// Lesson - id, teacher (manyToOne), student(ManyToMany), classroom(manyToOne?), dateTime, topic
	// Classroom - id, name, location, lessons(tu jakas relacja)

	// unit testy w junit lub groovy/spock
	// testy integracyjne z mockmvc, na realnej testowej bazie h2 - dane do bazy testowej laduj liquibase
	// jacoco - pokrycie testami minimum 80%

	//liquibase - zaimplementuj osobno dla testow i osobno dla aplikacji, niech zawsze laduje po 5 encji bazodanowych

	//obsluga bledow - exception handler
	// bledy - kazda encja ma miec swoj blad na wypadek nieznalezienia - rzucamy w service w roznych findach

	// walidacja - jakartowe wg uznania (np name @NotEmpty), wstawiamy je w commandach

	// w controllerach zawsze przyjmujemy commandy, np CreateStudentCommand/EditStudentCommand,
	// a zwracamy ResponseEntity<Dto> np StudentDto

	// walidacja tworzenia lekcji - jeden student nie moze w tej samej chwili miec dwoch lekcji (Zalozmy ze lekcja trwa godzine),
	// podobnie teacher tez nie moze miec dwoch lekcji w jednym czasie
	// ani w jednej sali nie moga sie w jednej chwili odbywac dwie lekcje

	// softDelete - usuwanie deletem nie kasuje encji z bazy danych, zmienia flage active na false

	// paginacja i sortowanie - przy findAll() nie chcemy zwrocic miliona wierszy :)

	// wysylka maili - przy tworzeniu lekcji wysylamy maila do studentow i teachera z prosba o potwierdzenie obecnosci
	//mailtrap

	// konwersje z command na encje i z encji na dto zrob za pomoca modelmapper




}
