# Funktionale Programmierung


### - Warum sind `reversed`, `debug` und `none` Funktionen höherer Ordnung?

- Sie nehmen andere Funktionen als Parameter.
- Sie geben andere Funktionen als Ergebnis zurück.

### - Welches Entwurfsmuster wurde durch die Verwendung von Funktionen höherer Ordnung realisiert?

Das **Decorator Pattern**.

### - Warum kann das Entwurfsmuster dadurch implementiert werden?

Das Decorator Pattern kann durch Funktionen höherer Ordnung implementiert werden, weil diese Funktionen zusätzliche Funktionalität zu bestehenden Funktionen hinzufügen können, ohne deren ursprüngliche Implementierung zu ändern.

### Grundlegende Struktur des Entwurfsmusters

- **Komponente**: Eine Schnittstelle oder abstrakte Klasse, die die grundlegenden Funktionen definiert.
- **Konkrete Komponente**: Eine Implementierung der Komponente.
- **Decorator**: Eine Klasse, die die Schnittstelle der Komponente implementiert und eine Instanz der Komponente hält, um zusätzliche Funktionalität hinzuzufügen.

### Korrelation mit Funktionen höherer Ordnung

Die Struktur des Decorator Patterns korreliert mit der Struktur von Funktionen höherer Ordnung:

- Funktionen höherer Ordnung nehmen eine Funktion als Parameter (entspricht der Komponente) und geben eine neue Funktion zurück, die zusätzliche Funktionalität enthält (entspricht dem Decorator).
