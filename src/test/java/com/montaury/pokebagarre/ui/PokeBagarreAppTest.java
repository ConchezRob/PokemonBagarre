package com.montaury.pokebagarre.ui;
import java.util.concurrent.TimeUnit;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
@ExtendWith(ApplicationExtension.class)

class PokeBagarreAppTest {
    private static final String IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_1 = "#nomPokemon1";
    private static final String IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_2 = "#nomPokemon2";
    private static final String IDENTIFIANT_BOUTON_BAGARRE = ".button";
    @Start
    private void start(Stage stage) {
        new PokeBagarreApp().start(stage);
    }
//    @Test
//    void nom_du_test(FxRobot robot) {
//        //robot.clickOn(IDENTIFIANT);
//        //robot.write("Text");
//        //await().atMost(5, TimeUnit.SECONDS).untilAsserted(() ->
//        //assertThat(...).isEqualTo(...)
//        // );
//    }


    @Test
    void devrait_afficher_le_resultat_de_la_bagarre(FxRobot robot) {
        // GIVEN
        robot.clickOn(IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_1);
        robot.write("Pikachu");
        robot.clickOn(IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_2);
        robot.write("Salamèche");
        // WHEN
        robot.clickOn(IDENTIFIANT_BOUTON_BAGARRE);
        // THEN
        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() ->
                assertThat(getResultatBagarre(robot)).isNotEmpty()
        );
    }

    @Test
    void devrait_afficher_une_erreur_si_le_premier_pokemon_pas_renseigne(FxRobot robot) {
        //GIVEN
        robot.clickOn(IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_2);
        robot.write("Pikachu");

        //WHEN
        robot.clickOn(IDENTIFIANT_BOUTON_BAGARRE);

        //THEN
        await().atMost(5,TimeUnit.SECONDS).untilAsserted(() ->
                assertThat(getMessageErreur(robot)).isEqualTo("Erreur: Le premier pokemon n'est pas renseigne")
        );
    }

    @Test
    void devrait_afficher_une_erreur_si_le_second_pokemon_pas_renseigne(FxRobot robot) {
        //GIVEN
        robot.clickOn(IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_1);
        robot.write("Pikachu");

        //WHEN
        robot.clickOn(IDENTIFIANT_BOUTON_BAGARRE);

        //THEN
        await().atMost(5,TimeUnit.SECONDS).untilAsserted(() ->
                assertThat(getMessageErreur(robot)).isEqualTo("Erreur: Le second pokemon n'est pas renseigne")
        );
    }

    @Test
    void devrait_afficher_une_erreur_si_le_ppremier_pokemon_existe_pas(FxRobot robot) {
        // GIVEN
        String pokemonExistePas = "bhejvfzjf";
        robot.clickOn(IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_1);
        robot.write(pokemonExistePas);
        robot.clickOn(IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_2);
        robot.write("Pikachu");

        // WHEN
        robot.clickOn(IDENTIFIANT_BOUTON_BAGARRE);

        // THEN
        await().atMost(5,TimeUnit.SECONDS).untilAsserted(() ->
                assertThat(getMessageErreur(robot)).isEqualTo("Erreur: Impossible de recuperer les details sur '"+pokemonExistePas+"'")
        );
    }

    @Test
    void devrait_afficher_une_erreur_si_les_deux_pokemon_sont_le_meme(FxRobot robot) {
        // GIVEN
        robot.clickOn(IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_1);
        robot.write("Pikachu");
        robot.clickOn(IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_2);
        robot.write("Pikachu");
        // WHEN
        robot.clickOn(IDENTIFIANT_BOUTON_BAGARRE);
        // THEN
        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() ->
                assertThat(getMessageErreur(robot)).isEqualTo("Erreur: Impossible de faire se bagarrer un pokemon avec lui-meme")
        );
    }

    private static String getResultatBagarre(FxRobot robot) {
        return robot.lookup("#resultatBagarre").queryText().getText();
    }

    private static String getMessageErreur(FxRobot robot) {
        return robot.lookup("#resultatErreur").queryLabeled().getText();
    }
}