package logic;

public class MedicamentSearchResult
{

    private String name;
    private String form;
    private String dosage;
    private boolean matin;
    private boolean midi;
    private boolean soir;

    public MedicamentSearchResult(String name, String form, String dosage, boolean matin, boolean midi, boolean soir) {
        this.name = name;
        this.form = form;
        this.dosage = dosage;
        this.matin = matin;
        this.midi = midi;
        this.soir = soir;
    }





    public String getName() {
        return name;
    }

    public String getForm() {
        return form;
    }

    public String getDosage() {
        return dosage;
    }

    public boolean isMatin() {
        return matin;
    }

    @Override
    public String toString() {
        return "MedicamentSearchResult{" +
                "name='" + name + '\'' +
                ", form='" + form + '\'' +
                ", dosage='" + dosage + '\'' +
                ", matin=" + matin +
                ", midi=" + midi +
                ", soir=" + soir +
                '}';
    }

    public boolean isMidi() {
        return midi;
    }

    public boolean isSoir() {
        return soir;
    }
}
