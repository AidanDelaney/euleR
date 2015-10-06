# euleR - An R interface to the VenNom drawing library

## Usage

## Architecture

euleR is an R package.  This means that it has to have the R structure and not the more "java standard" maven project structure.  As such euleR pulls in a number of java projects (in maven form) from it's src-java folder.  In particular, we have a dependency on vennom (core drawing algorithm) and venn-diagram-types (reporting).  Köttlebröcke is the glue between euleR and vennom.  In essence, euleR is a slightly modified version of the venneuler R package which uses Köttlebröcke to map types used in vennom into the types expected by euleR.

euleR builds the transitive closure of its dependencies.  So the deps are a little large.  However, this significantly simplifies deployment as we then don't have to require that R users have a maven registry of the dependencies.

## Publications
