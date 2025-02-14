package com.montaury.pokebagarre.metier;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @brief Tests unitaires pour la classe Pokemon
 * @details Cette classe permet de tester la fonction estVainqueurContre de la classe Pokemon
 */
class PokemonTest {


    /**
     * @brief Test de la fonction estVainqueurContre
     * @details Teste si le pokemon1 est vainqueur contre le pokemon2 si son attaque est supérieur
     */
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

    /**
     * @brief Test de la fonction estVainqueurContre
     * @details Teste si le pokemon1 est vainqueur contre le pokemon2 si son attaque est égal et sa défense est supérieur
     */
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

    /**
     * @brief Test de la fonction estVainqueurContre
     * @details Teste si le pokemon1 est perdant contre le pokemon2 si son attaque est inférieur
     */
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

    /**
     * @brief Test de la fonction estVainqueurContre
     * @details Teste si le pokemon1 est perdant contre le pokemon2 si son attaque est égal et sa défense est inférieur
     */
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

    /**
     * @brief Test de la fonction estVainqueurContre
     * @details Teste si le pokemon1 est gagnant contre le pokemon2 si son attaque et sa défense sont égal
     */
    @Test
    void pokemon1_gagnant_contre_pokemon2_par_attaque_egal_defense_egal(){
        //GIVEN
        Pokemon pokemon1 = new Pokemon("Pikachu", "", new Stats(10, 10));
        Pokemon pokemon2 = new Pokemon("Bulbizarre", "", new Stats(10, 10));

        //WHEN
        boolean result = pokemon1.estVainqueurContre(pokemon2);

        //THEN
        assertTrue(result);
    }
}