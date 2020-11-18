package com.data.myfirstdata;

import com.github.javafaker.Faker;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonController {
    private PersonJpaRepo personJpaRepo;
    private JobLauncher jobLauncher;
    private Job personsImportJob;

    @Autowired
    public PersonController(PersonJpaRepo personJpaRepo, JobLauncher jobLauncher, Job personsImportJob) {
        this.personJpaRepo = personJpaRepo;
        this.jobLauncher = jobLauncher;
        this.personsImportJob = personsImportJob;
    }

    @PostMapping("/persons")
    public void addRandomPersonToDatabase() {
        insertRandomPersonInDB();
    }

    @GetMapping("/persons/{minAge}/{maxAge}/byAddress")
    public List<PersonConcreteClassProjection> getPersonsWithAgeBetweenByAddress(@PathVariable int minAge, @PathVariable int maxAge){
        return personJpaRepo.findByAgeBetween(minAge, maxAge, PersonConcreteClassProjection.class);
    }
    @GetMapping("/persons/{minAge}/{maxAge}/byNames")
    public List<Person> getPersonsWithAgeBetweenByNames(@PathVariable int minAge, @PathVariable int maxAge){
        List<Person> firstNameAndLastName = personJpaRepo.findByAgeBetweenWithJpql(minAge, maxAge);
        return firstNameAndLastName;
    }

    @PostMapping("persons/batch")
    public void startBatch() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        jobLauncher.run(personsImportJob, new JobParameters());
    }

//    @PostConstruct
    public void insertRandomPersonsAtSpringStartup() {
        for (int i = 0; i < 10; i++) {
            insertRandomPersonInDB();
        }
    }

    private void insertRandomPersonInDB() {
        Faker faker = new Faker();
        Person randomPerson = new Person(
                faker.name().firstName(), faker.funnyName().name(), faker.address().streetAddress(),
                faker.number().numberBetween(10, 45)
        );
        personJpaRepo.save(randomPerson);
    }
}
