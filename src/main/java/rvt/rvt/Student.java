package rvt;


public class Student {
    private String vards;
    private String uzvards;
    private String epasts;
    private String personasKods;
    private String registracijasLaiks;

    public Student(String vards, String uzvards, String epasts, String personasKods, String registracijasLaiks) {
        this.vards = vards;
        this.uzvards = uzvards;
        this.epasts = epasts;
        this.personasKods = personasKods;
        this.registracijasLaiks = registracijasLaiks;
    }

    // Getteri nepieciešami datu apstrādei
    public String getPersonasKods() { return personasKods; }
    
    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s", vards, uzvards, epasts, personasKods, registracijasLaiks);
    }

    // Masīvs formatētai izvadei (tabulai)
    public String[] toArray() {
        return new String[]{vards, uzvards, epasts, personasKods, registracijasLaiks};
    }
}