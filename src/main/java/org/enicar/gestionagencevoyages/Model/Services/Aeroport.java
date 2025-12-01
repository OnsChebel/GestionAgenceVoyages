package org.enicar.gestionagencevoyages.Model.Services;

public record Aeroport(String nom, String codeIATA) {
    @Override
    public String toString() {
        return nom + " (" + codeIATA + ")";
    }
}
