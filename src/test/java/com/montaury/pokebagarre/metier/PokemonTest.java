package com.montaury.pokebagarre.metier;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PokemonTest {


    @Test
    void pokemon1_estVainqueurContre_pokemon2_par_attaque() {
        //GIVEN
        Pokemon pokemon1 = new Pokemon("Pikachu", "", new Stats(10, 10));
        Pokemon pokemon2 = new Pokemon("Bulbizarre", "", new Stats(5, 5));

        //WHEN
        boolean result = pokemon1.estVainqueurContre(pokemon2);

        //THEN
        assertTrue(result);
    }

    @Test
    void pokemon1_estVainqueurContre_pokemon2_par_attaque_eqale_defense_superieur(){
        //GIVEN
        Pokemon pokemon1 = new Pokemon("Pikachu", "", new Stats(10, 10));
        Pokemon pokemon2 = new Pokemon("Bulbizarre", "", new Stats(10, 5));

        //WHEN
        boolean result = pokemon1.estVainqueurContre(pokemon2);

        //THEN
        assertTrue(result);
    }

    @Test
    void pokemon1_perdant_contre_pokemon2_par_attaque(){
        //GIVEN
        Pokemon pokemon1 = new Pokemon("Pikachu", "", new Stats(5, 5));
        Pokemon pokemon2 = new Pokemon("Bulbizarre", "", new Stats(10, 10));

        //WHEN
        boolean result = pokemon1.estVainqueurContre(pokemon2);

        //THEN
        assertFalse(result);
    }

    @Test
    void pokemon1_perdant_contre_pokemon2_par_attaque_egal_defense_inferieur(){
        //GIVEN
        Pokemon pokemon1 = new Pokemon("Pikachu", "", new Stats(10, 5));
        Pokemon pokemon2 = new Pokemon("Bulbizarre", "", new Stats(10, 10));

        //WHEN
        boolean result = pokemon1.estVainqueurContre(pokemon2);

        //THEN
        assertFalse(result);
    }

    @Test
    void pokemon1_perdant_contre_pokemon2_par_attaque_egal_defense_egal(){
        //GIVEN
        Pokemon pokemon1 = new Pokemon("Pikachu", "", new Stats(10, 10));
        Pokemon pokemon2 = new Pokemon("Bulbizarre", "", new Stats(10, 10));

        //WHEN
        boolean result = pokemon1.estVainqueurContre(pokemon2);

        //THEN
        assertTrue(result);
    }
}