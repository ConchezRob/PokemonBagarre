package com.montaury.pokebagarre.metier;

import com.montaury.pokebagarre.fixtures.ConstructeurDePokemon;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

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
        Pokemon pokemon1 = new ConstructeurDePokemon().avecAttaque(10).avecDefense(5).construire();
        Pokemon pokemon2 = new ConstructeurDePokemon().avecAttaque(5).avecDefense(5).construire();

        //WHEN
        boolean result = pokemon1.estVainqueurContre(pokemon2);

        //THEN
        assertThat(result).isTrue();
    }

    /**
     * @brief Test de la fonction estVainqueurContre
     * @details Teste si le pokemon1 est vainqueur contre le pokemon2 si son attaque est égal et sa défense est supérieur
     */
    @Test
    void pokemon1_estVainqueurContre_pokemon2_par_attaque_eqale_defense_superieur(){
        //GIVEN
        Pokemon pokemon1 = new ConstructeurDePokemon().avecAttaque(10).avecDefense(10).construire();
        Pokemon pokemon2 = new ConstructeurDePokemon().avecAttaque(10).avecDefense(5).construire();

        //WHEN
        boolean result = pokemon1.estVainqueurContre(pokemon2);

        //THEN
        assertThat(result).isTrue();
    }

    /**
     * @brief Test de la fonction estVainqueurContre
     * @details Teste si le pokemon1 est perdant contre le pokemon2 si son attaque est inférieur
     */
    @Test
    void pokemon1_perdant_contre_pokemon2_par_attaque(){
        //GIVEN
        Pokemon pokemon1 = new ConstructeurDePokemon().avecAttaque(5).avecDefense(5).construire();
        Pokemon pokemon2 = new ConstructeurDePokemon().avecAttaque(10).avecDefense(5).construire();

        //WHEN
        boolean result = pokemon1.estVainqueurContre(pokemon2);

        //THEN
        assertThat(result).isFalse();
    }

    /**
     * @brief Test de la fonction estVainqueurContre
     * @details Teste si le pokemon1 est perdant contre le pokemon2 si son attaque est égal et sa défense est inférieur
     */
    @Test
    void pokemon1_perdant_contre_pokemon2_par_attaque_egal_defense_inferieur(){
        //GIVEN
        Pokemon pokemon1 = new ConstructeurDePokemon().avecAttaque(10).avecDefense(5).construire();
        Pokemon pokemon2 = new ConstructeurDePokemon().avecAttaque(10).avecDefense(10).construire();

        //WHEN
        boolean result = pokemon1.estVainqueurContre(pokemon2);

        //THEN
        assertThat(result).isFalse();
    }

    /**
     * @brief Test de la fonction estVainqueurContre
     * @details Teste si le pokemon1 est gagnant contre le pokemon2 si son attaque et sa défense sont égal
     */
    @Test
    void pokemon1_gagnant_contre_pokemon2_par_attaque_egal_defense_egal(){
        //GIVEN
        Pokemon pokemon1 = new ConstructeurDePokemon().avecAttaque(10).avecDefense(10).construire();
        Pokemon pokemon2 = new ConstructeurDePokemon().avecAttaque(10).avecDefense(10).construire();

        //WHEN
        boolean result = pokemon1.estVainqueurContre(pokemon2);

        //THEN
        assertThat(result).isTrue();
    }
}