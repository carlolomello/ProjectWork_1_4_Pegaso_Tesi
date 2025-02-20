# ProjectWork_1_4_Pegaso_Tesi

## Tema  
**Tema n. 1** - La digitalizzazione dell’impresa  
**Traccia 1.4** - Sviluppo di una pagina web per un servizio di prenotazione online di un’impresa del settore terziario  

---

## Manuali d'uso  
Di seguito i manuali d'uso per gli utenti dei due applicativi:  

- [Manuale d'uso - Utenza Cliente](https://github.com/carlolomello/ProjectWork_1_4_Pegaso_Tesi/blob/main/ManualeUtenteCliente.pdf)  

- [Manuale d'uso - Amministrazione Hotel](https://github.com/carlolomello/ProjectWork_1_4_Pegaso_Tesi/blob/main/ManualeAmministrazioneHotel.pdf)  

---

## Struttura del progetto  
Il progetto è organizzato in **tre macro cartelle**, ognuna contenente un'applicazione specifica:  

### 1. traccia_1_4_RESTful (Spring Boot - Java)  
Questa cartella contiene l'applicazione backend sviluppata in **Spring Boot**. Per eseguirla è necessario:  
- Avere installato **JDK** (Java Development Kit).  
- Il punto di ingresso principale dell'applicazione è la classe `Traccia14ResTfulApplication`, reperibile al seguente percorso:  
    [Traccia14ResTfulApplication.java](https://github.com/carlolomello/ProjectWork_1_4_Pegaso_Tesi/blob/main/traccia_1_4_RESTful/src/main/java/com/lomello_MAT0312401017/traccia_1_4_RESTful/Traccia14ResTfulApplication.java)  
- Prima di avviare l'applicazione, è necessario configurare il file **application.properties** con le credenziali corrette. Il file si trova qui:  
[application.properties](https://github.com/carlolomello/ProjectWork_1_4_Pegaso_Tesi/blob/main/traccia_1_4_RESTful/src/main/resources/application.properties)  


---

### 2. traccia_1_4_Salesforce (Salesforce Integration)  
Questa cartella contiene l'integrazione con **Salesforce**. Per utilizzarla è necessario:  
- Avere installato **JDK** e **Salesforce CLI**.  
- Accedere all'org Salesforce all'indirizzo:  
  👉 [Pegaso Salesforce Org](https://pegaso-dev-ed.develop.my.salesforce.com)  

(Se non si dispone delle credenziali di accesso, è possibile richiederle scrivendo a **carlo.lomello@yahoo.it**.)

- Per replicare le funzionalità sulla propria org Salesforce:  
    1. Effettuare l'autenticazione con Salesforce CLI.  
    2. Eseguire il deploy dei metadati utilizzando il **manifest**:  
        [package.xml](https://github.com/carlolomello/ProjectWork_1_4_Pegaso_Tesi/blob/main/traccia_1_4_Salesforce/manifest/package.xml)  


---

### 3. traccia_1_4_pwa_hotel (React PWA - Frontend)  
Questa cartella contiene l'applicazione frontend sviluppata in **React**. Per eseguirla è necessario:  
- Avere installato **Node.js**.  
- Installare le dipendenze eseguendo il comando:  
```bash
    npm install
```
dalla root del progetto.

- Avviare l'applicazione con:
```bash
    npm start
```

