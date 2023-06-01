package com.schoolmanagement.entity.concretes;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class AdvisorTeacher implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UserRole userRole; //UserRole concrete class olusturmamizin nedeni, ileriye dogru yeni roller eklenebileceginden, herhangi bir degisiklikte bircok
                              // yerde degisiklik yapilmasina neden olmasidir.

    //!!! Teacher, Student, Meet
    @OneToOne
    private Teacher teacher;

    @OneToMany(mappedBy = "advisorTeacher",orphanRemoval = true,cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    private List<Student> students;

    @OneToMany(mappedBy = "advisorTeacher",cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Meet> meets;
}
/*
    @Builder anotasyon eklenen class için bir Builder DPattern oluşturuyor. Default da toBuilder özelliği false.

        Builder neseyi Örnek örnek = Örnek.özellik1().özellik2().build gibi build etmeye yararken,
        toBuilder olan bir nesnenin üzerinden farklı bir nesne yapmaya yarıyor.
        Örnek yeni örnek = örnek.toBuilder().özellik1(farklı bir özellik).build*/
