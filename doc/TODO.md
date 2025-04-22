# TODO

- optimiser les maj avec les méthodes de DocumentEvent "getOffset()" et "getLength()"
- ajouter menu déroulant avec configs par défaut (configurations pour cible/remplacement/estRegex pour des taches classiques)
- gérer les exceptions de saisies de regex :
  - divers : Exception in thread "AWT-EventQueue-0" java.lang.IllegalArgumentException: Illegal group reference: group index is missing
  - $1 dans remplacement et rien dans cible : Exception in thread "AWT-EventQueue-0" java.lang.IndexOutOfBoundsException: No group 1
- ajouter fonctionnalités complémentaires
  - count word / char / {nb apparition du regex}
  - import de fichier
  - export fichier
  - import de config perso regex
  - export de config perso regex
