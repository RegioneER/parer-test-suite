# ParER Test Suite (progetto Ping)

Fonte template redazione documento:  https://www.makeareadme.com/.


# Descrizione

Il seguente progetto è utilizzato come **dipendenza** interna di PING (PreINGest) al fine di introdurre un livello di "astrazione" legato all'implementazione delle suite di test al suo interno.

# Installazione

Come già specificato nel paragrafo precedente [Descrizione](# Descrizione) si tratta di un progetto di tipo "libreria", quindi un modulo applicativo utilizzato attraverso la definzione della dipendenza Maven secondo lo standard previsto (https://maven.apache.org/): 

```xml
<dependency>
    <groupId>it.eng.parer</groupId>    
    <artifactId>parer-test-suite</artifactId>
    <version>$VERSIONE</version>
    <scope>test</scope>
</dependency>
```

# Utilizzo

Come già specificato nella descrizione, attraverso questa libreria sono state definite un set di entità utilizzate come classi "utility" nella reealizzazione di test di unità all'interno del progetto PING (PreINGest), per ulterio dettagli e specifihe consultare i relativi Javadoc generati dal plugin [maven-javadoc-plugin](https://maven.apache.org/plugins/maven-javadoc-plugin/).

# Supporto

Mantainer del progetto è [Engineering Ingegneria Informatica S.p.A.](https://www.eng.it/).

# Contributi

Se interessati a crontribuire alla crescita del progetto potete scrivere all'indirizzo email <a href="mailto:areasviluppoparer@regione.emilia-romagna.it">areasviluppoparer@regione.emilia-romagna.it</a>.

# Credits

Progetto di proprietà di [Regione Emilia-Romagna](https://www.regione.emilia-romagna.it/) sviluppato a cura di [Engineering Ingegneria Informatica S.p.A.](https://www.eng.it/).

# Licenza

Questo progetto è rilasciato sotto licenza GNU Affero General Public License v3.0 or later ([LICENSE.txt](LICENSE.txt)).
