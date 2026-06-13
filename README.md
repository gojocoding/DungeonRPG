# DungeonRPG
Progetto di Metodologie di Programmazione 

Dungeon RPG è un’applicazione in Java che implementa un gioco di ruolo a turni.
Il giocatore esplora un dungeon composto da stanze casuali, affronta nemici, eventi e un boss finale.
Il progetto è strutturato per essere estendibile e supporta il salvataggio e caricamento della partita tramite file JSON.

🚀 Come eseguire il progetto
Prerequisiti
Java 25 (LTS)
Gradle

Build del progetto
./gradlew build

🎮 Funzionalità principali
Creazione personaggio (Guerriero / Mago)
Dungeon generato proceduralmente
Combattimenti a turni
Eventi casuali nelle stanze
Boss finale
Sistema di livello ed esperienza
Salvataggio e caricamento partita in JSON
Interfaccia grafica (JavaFX)

🧱 Architettura del progetto (sintesi)

Il progetto è strutturato in package separati per responsabilità:

core → gestione del flusso di gioco (GameEngine)
model → entità del gioco (Player, Enemy, Room, ecc.)
world → generazione dungeon e stanze
persistence → salvataggio e caricamento JSON
ui → interfaccia grafica JavaFX

L’architettura è pensata per facilitare:

estensione di nuove stanze e nemici
aggiunta di nuovi sistemi di gioco
possibile futura migrazione verso web o mobile

💾 Persistenza dati

Il gioco utilizza un sistema di salvataggio basato su JSON tramite libreria Gson.

I dati salvati includono:

stato del giocatore
livello ed esperienza
HP e statistiche
progressione nel dungeon

