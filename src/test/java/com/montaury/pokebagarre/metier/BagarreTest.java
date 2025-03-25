package com.montaury.pokebagarre.metier;

import com.montaury.pokebagarre.erreurs.ErreurBagarre;
import com.montaury.pokebagarre.erreurs.ErreurMemePokemon;
import com.montaury.pokebagarre.erreurs.ErreurPokemonNonRenseigne;
import com.montaury.pokebagarre.erreurs.ErreurRecuperationPokemon;
import com.montaury.pokebagarre.fixtures.ConstructeurDePokemon;
import com.montaury.pokebagarre.webapi.PokeBuildApi;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.configuration.injection.filter.OngoingInjector;
import org.mockito.stubbing.OngoingStubbing;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class BagarreTest {

    private PokeBuildApi fausseApi = Mockito.mock(PokeBuildApi.class);;

    @Test
    void devrait_lever_exception_quand_premier_pokemon_non_renseigne(){
        //GIVEN
        Bagarre bagarre = new Bagarre();
        String nomPremierPokemon = null;
        String nomSecondPokemon = "Pikachu";

        //WHEN
        Throwable exception = catchThrowable(() -> bagarre.demarrer(nomPremierPokemon, nomSecondPokemon));

        //THEN
        assertThat(exception).isInstanceOf(ErreurPokemonNonRenseigne.class).hasMessage("Le premier pokemon n'est pas renseigne");
    }

    @Test
    void devrait_lever_exception_quand_second_pokemon_non_renseigne(){
        //GIVEN
        Bagarre bagarre = new Bagarre();
        String nomPremierPokemon = "Pikachu";
        String nomSecondPokemon = null;

        //WHEN
        Throwable exception = catchThrowable(() -> bagarre.demarrer(nomPremierPokemon, nomSecondPokemon));

        //THEN
        assertThat(exception).isInstanceOf(ErreurPokemonNonRenseigne.class).hasMessage("Le second pokemon n'est pas renseigne");
    }

    @Test
    void devrait_lever_exception_quand_meme_pokemon(){
        //GIVEN
        Bagarre bagarre = new Bagarre();
        String nomPremierPokemon = "Pikachu";
        String nomSecondPokemon = "Pikachu";

        //WHEN
        Throwable exception = catchThrowable(() -> bagarre.demarrer(nomPremierPokemon, nomSecondPokemon));

        //THEN
        assertThat(exception).isInstanceOf(ErreurMemePokemon.class).hasMessage("Impossible de faire se bagarrer un pokemon avec lui-meme");
    }

    @Test
    void devrait_lever_exception_quand_premier_pokemon_vide() {
        //GIVEN
        Bagarre bagarre = new Bagarre();
        String nomPremierPokemon = "";
        String nomSecondPokemon = "Pikachu";

        //WHEN
        Throwable exception = catchThrowable(() -> bagarre.demarrer(nomPremierPokemon, nomSecondPokemon));

        //THEN
        assertThat(exception).isInstanceOf(ErreurPokemonNonRenseigne.class).hasMessage("Le premier pokemon n'est pas renseigne");
    }

    @Test
    void devrait_lever_exception_quand_second_pokemon_vide() {
        //GIVEN
        Bagarre bagarre = new Bagarre();
        String nomPremierPokemon = "Pikachu";
        String nomSecondPokemon = "";

        //WHEN
        Throwable exception = catchThrowable(() -> bagarre.demarrer(nomPremierPokemon, nomSecondPokemon));

        //THEN
        assertThat(exception).isInstanceOf(ErreurPokemonNonRenseigne.class).hasMessage("Le second pokemon n'est pas renseigne");
    }


    //AVEC API

    @Test
    void devrait_lever_exception_quand_premier_pokemon_existe_pas() {
        //GIVEN
        Bagarre bagarre = new Bagarre(fausseApi);
        String nomPremierPokemon = "dhahdadj";
        String nomSecondPokemon = "Pikachu";

        //WHEN
        Mockito.when(fausseApi.recupererParNom(nomPremierPokemon))
            .thenReturn(CompletableFuture.failedFuture(new ErreurRecuperationPokemon(nomPremierPokemon))
        );
        Mockito.when(fausseApi.recupererParNom(nomSecondPokemon)).thenReturn(CompletableFuture.completedFuture(new Pokemon(nomSecondPokemon, null, new Stats(10,10))));

        CompletableFuture<Pokemon> pokemon = bagarre.demarrer(nomPremierPokemon, nomSecondPokemon);

        //THEN
        assertThat(pokemon)
                .failsWithin(Duration.ofSeconds(2))
                .withThrowableOfType(ExecutionException.class)
                .havingCause()
                .isInstanceOf(ErreurRecuperationPokemon.class)
                .withMessage("Impossible de recuperer les details sur 'dhahdadj'");
    }

    @Test
    void devrait_lever_exception_quand_second_pokemon_existe_pas() {
        //GIVEN
        Bagarre bagarre = new Bagarre(fausseApi);
        String nomPremierPokemon = "Pikachu";
        String nomSecondPokemon = "dhahdadj";

        //WHEN
        Mockito.when(fausseApi.recupererParNom(nomPremierPokemon)).thenReturn(CompletableFuture.completedFuture(new Pokemon(nomPremierPokemon, null, new Stats(10,10))));
        Mockito.when(fausseApi.recupererParNom(nomSecondPokemon))
                .thenReturn(CompletableFuture.failedFuture(new ErreurRecuperationPokemon(nomSecondPokemon))
                );

        CompletableFuture<Pokemon> pokemon = bagarre.demarrer(nomPremierPokemon, nomSecondPokemon);

        //THEN
        assertThat(pokemon)
                .failsWithin(Duration.ofSeconds(2))
                .withThrowableOfType(ExecutionException.class)
                .havingCause()
                .isInstanceOf(ErreurRecuperationPokemon.class)
                .withMessage("Impossible de recuperer les details sur 'dhahdadj'");
    }

    @Test
    void devrait_pokemon1_gagnant_pokemon2(){
        //GIVEN
        Bagarre bagarre = new Bagarre(fausseApi);
        String nomPremierPokemon = "Pikachu";
        String nomSecondPokemon = "Salameche";
        Pokemon pokemon1 = new ConstructeurDePokemon().avecAttaque(10).avecDefense(10).construire();
        Pokemon pokemon2 = new ConstructeurDePokemon().avecAttaque(10).avecDefense(5).construire();

        //WHEN
        Mockito.when(fausseApi.recupererParNom("Pikachu")).thenReturn(CompletableFuture.completedFuture(pokemon1));
        Mockito.when(fausseApi.recupererParNom("Salameche")).thenReturn(CompletableFuture.completedFuture(pokemon2));

        CompletableFuture<Pokemon> pokemonVainqueur = bagarre.demarrer(nomPremierPokemon, nomSecondPokemon);

        //THEN
        assertThat(pokemonVainqueur)
                .succeedsWithin(Duration.ofSeconds(2))
                .satisfies(pokemon->{
                    assertThat(pokemon).isEqualTo(pokemon1);
                });
    }

    @Test
    void devrait_pokemon2_gagnant_pokemon1(){
        //GIVEN
        Bagarre bagarre = new Bagarre(fausseApi);
        String nomPremierPokemon = "Pikachu";
        String nomSecondPokemon = "Salameche";
        Pokemon pokemon1 = new ConstructeurDePokemon().avecAttaque(5).avecDefense(5).construire();
        Pokemon pokemon2 = new ConstructeurDePokemon().avecAttaque(10).avecDefense(10).construire();

        //WHEN
        Mockito.when(fausseApi.recupererParNom("Pikachu")).thenReturn(CompletableFuture.completedFuture(pokemon1));
        Mockito.when(fausseApi.recupererParNom("Salameche")).thenReturn(CompletableFuture.completedFuture(pokemon2));

        CompletableFuture<Pokemon> pokemonVainqueur = bagarre.demarrer(nomPremierPokemon, nomSecondPokemon);

        //THEN
        assertThat(pokemonVainqueur)
                .succeedsWithin(Duration.ofSeconds(2))
                .satisfies(pokemon->{
                    assertThat(pokemon).isEqualTo(pokemon2);
                });
    }
}