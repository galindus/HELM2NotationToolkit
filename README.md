# HELM2 Notation Toolkit #


The HELM2NotationToolkit contains functionality to parse HELM2 notation and create a HELM2Notation object. This allows the user to perform calculations, validate HELM strings and manipulate HELM molecules via the toolkit. 

The toolkit is normally accessed via the HELM2Webservice and calls other packages for some processes. See 


## Getting started ##

Releases can be found in the [Nexus repository](https://oss.sonatype.org/#nexus-search;quick~helm2-notationtoolkit)

- Dependency information can be found in the POM 
- Object model information can be found in the Java doc. 

Please check version numbers carefully.


## Configuration ##


There are two new configuration files in .helm.

The Chemistry.property contains the information which Chemistry plugin you want to use. The default one is CDK. 

    chemistry=org.helm.chemtoolkit.cdk.CDKManipulator

The MonomerStoreConfig.properties contains the information if you want to use a webservice to perform monomer management actions.

    use.webservice=false update.automatic=true webservice.monomers.url=http://localhost:8080
    webservice.monomers.path=DemoWebserver/service/monomerStore?
    polymerType=#webservice.monomers.put.path=path/monomerToolkit 
    webservice.monomers.put.path=DemoWebserver/service/monomerStore 
    webservice.nucleotides.url=http://localhost:8080 webservice.nucleotides.path=DemoWebserver/service/nucleotideStore #webservice.nucleotides.put.path=path/nucleotideTemplate 
    webservice.nucleotides.put.path=DemoWebserver/service/nucleotidestore 
    webservice.editor.categorization.url=http://localhost:8080 
    webservice.editor.categorization.path=path/monomerStoreEditorCategories
    



## Defects ##

Please let us know by logging in the issue list. Even better - send us a fix via a pull request!


##  Further information ##

See the HELM [wiki](https://pistoiaalliance.atlassian.net/wiki/spaces/PUB/pages/14286877/HELM+Toolkit) for additional documentation and user guides
