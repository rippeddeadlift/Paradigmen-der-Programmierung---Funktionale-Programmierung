# Funktionale Programmierung

## 1.2 b) Warum sind reversed, debug und none Funktionen höherer Ordnung?

- Sie nehmen andere Funktionen als Parameter.
- Sie geben andere Funktionen als Ergebnis zurück.

## 1.2 b) Welches Entwurfsmuster wurde durch die Verwendung von Funktionen höherer Ordnung realisiert?

Das Decorator Pattern.

## 1.2 b) Warum kann das Entwurfsmuster dadurch implementiert werden?

Das Decorator Pattern kann durch Funktionen höherer Ordnung implementiert werden, weil diese Funktionen zusätzliche
Funktionalität zu bestehenden Funktionen hinzufügen können, ohne deren ursprüngliche Implementierung zu ändern.

## 1.2 b) Grundlegende Struktur des Entwurfsmusters

- Komponente: Eine Schnittstelle oder abstrakte Klasse, die die grundlegenden Funktionen definiert.
- Konkrete Komponente: Eine Implementierung der Komponente.
- Decorator: Eine Klasse, die die Schnittstelle der Komponente implementiert und eine Instanz der Komponente hält,
  um zusätzliche Funktionalität hinzuzufügen.

## 1.2 b) Korrelation mit Funktionen höherer Ordnung

Die Struktur des Decorator Patterns korreliert mit der Struktur von Funktionen höherer Ordnung:

- Funktionen höherer Ordnung nehmen eine Funktion als Parameter (entspricht der Komponente) und geben eine neue Funktion
  zurück, die zusätzliche Funktionalität enthält (entspricht dem Decorator).

## 2a) Korrelation mit Funktionen höherer Ordnung

- Unit

## 2b) Either<A?, B> und Either<A, B>?

- sind nicht äquivalent, da

### a + 1 + b != a+1+b+1

## 2c) a^0 = 1 in Typen

### fun A to(f: (Nothing) -> A): Unit = Unit

### fun A from(unit: Unit): (Nothing) -> A = { throw NoSuchElementException("No value present") }

## 2d) Warum kann die from-Funktion implementiert werden

- Mit Nothing können wir schauen,dass es keinen Wert gibt und eine Ausnahme ausgelöst wird, wenn versucht wird, den
  Rückgabewert dieser Funktion zu verwenden.

## 2e) TODO ()

## 2f) Warum ist die Implementierung von makeEither nicht 100 %ig valide?

- nullPointerException

## 3a) Weitere Merkmale der funktionalen Programmierung

- Unveränderlichkeit: Alle Variablen sind unveränderlich. Zum Beispiel wird in der Funktion reversed die
  Eingabe ord nicht verändert.

- Reine Funktionen: Alle Funktionen sind rein, d.h., sie haben keine Seiteneffekte und liefern bei gleichen Eingaben
  immer die gleiche Ausgabe. Die Funktion intOrd ist ein Beispiel für eine reine Funktion.

- Higher-Order Funktionen: Es gibt mehrere Funktionen, die andere Funktionen als Argumente akzeptieren oder
  Funktionen zurückgeben. reversed, contraMap, zip sind ein Beispiel für Higher-Order Funktionen.

- Funktionen als First-Class Citizens: Funktionen werden wie jede andere Variable behandelt. Sie können als
  Argumente an andere Funktionen übergeben und von anderen Funktionen zurückgegeben werden. Dies erleichtert die
  Abstraktion und Modularisierung von Code.


#### nothing ist ein subtype von jedem anderen Type