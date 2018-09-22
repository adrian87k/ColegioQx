/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.quipux.colegioquipux.models.entity;

import java.util.Objects;

/**
 *
 * @author salim.castellanos
 */
public class Matricula {
   
   private String alumno;
   private Long asignatura;

    public Matricula(String alumno, Long asignatura) {
      
    }

    

    @Override
    public String toString() {
        return "Matricula{" + "alumno=" + alumno + ", asignatura=" + asignatura + ", year=" + year + ", nota=" + nota + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + Objects.hashCode(this.alumno);
        hash = 97 * hash + (int) (this.asignatura ^ (this.asignatura >>> 32));
        hash = 97 * hash + this.year;
        hash = 97 * hash + Objects.hashCode(this.nota);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Matricula other = (Matricula) obj;
        if (this.alumno != other.alumno) {
            return false;
        }
        if (this.asignatura != other.asignatura) {
            return false;
        }
        if (this.year != other.year) {
            return false;
        }
        if (!Objects.equals(this.nota, other.nota)) {
            return false;
        }
        return true;
    }

    public String getAlumno() {
        return alumno;
    }

    public void setAlumno(String alumno) {
        this.alumno = alumno;
    }

    public Long getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(Long asignatura) {
        this.asignatura = asignatura;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public Matricula(String alumno, Long asignatura, int year) {
        this.alumno = alumno;
        this.asignatura = asignatura;
        this.year = year;
    }
   private int year;
   private Integer nota = null;

    public void setId(long aLong) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
