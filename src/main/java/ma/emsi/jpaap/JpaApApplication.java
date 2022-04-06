package ma.emsi.jpaap;

import ma.emsi.jpaap.entities.Patient;
import ma.emsi.jpaap.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class JpaApApplication implements CommandLineRunner {
    @Autowired
    private PatientRepository patientRepository;
    public static void main(String[] args) {
        SpringApplication.run(JpaApApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        for (int i=0;i<25;i++){
            patientRepository.save(new Patient(null,"hassan",new Date(),true,(int)(Math.random()*100)));

            patientRepository.save(new Patient(null,"imad",new Date(),false,(int)(Math.random()*100)));

            patientRepository.save(new Patient(null,"yaniss",new Date(),false,(int)(Math.random()*100)));

            patientRepository.save(new Patient(null,"lamiae",new Date(),false,(int)(Math.random()*100)));

        }

        Page<Patient> patients = patientRepository.findAll(PageRequest.of(1,10));
        System.out.println("Total pages:"+patients.getTotalPages());
        System.out.println("Total elements:"+patients.getTotalElements());
        System.out.println("Page Number:"+patients.getNumber());
        List<Patient> content = patients.getContent();
        Page<Patient> byMalade= patientRepository.findByMalade(true, PageRequest.of(0,4));
        byMalade.forEach(p->{
            System.out.println("_______________________________");
            System.out.println(p.getId());
            System.out.println(p.getNom());
            System.out.println(p.getScore());
            System.out.println(p.getDateNaissance());
            System.out.println(p.isMalade());
        });
        System.out.println("=============================");
        Patient patient=patientRepository.findById(1L).orElse(null); //ou findBy(new Long(1);//.orElseThrow(()->new RuntimeException("Patient Not Found"));
        List<Patient> patientList=patientRepository.chercherPatients1("%h",40);
        if(patient!=null){
            System.out.println(patient.getNom());
            System.out.println(patient.isMalade());
        }
        patient.setScore(870);
        patientRepository.save(patient);

    }
}
