#-------------------------------------------------------------------------------
# Copyright (C) 2018 hungpham.
# All rights reserved. This program and the accompanying materials are belonged to the MorphoBoid group of Image and Sound team in the LaBRI (UMR 5800) and the Ausonius Institute (UMR 5607)
# 
# http://morphoboid.labri.fr/projects.html
# 
# Contributors:
#     hungpham - initial implementation of simulation of papyrus fragments assembly in 2D space. Version: 1.0
#-------------------------------------------------------------------------------
-------------------------------------------------------------
-------------------------------------------------------------
-   Simulation of papyrus fragmentsassembly in 2D space     -
-------------------------------------------------------------
-------------------------------------------------------------

https://forge.pole-aquinetic.net/RealityTechPublic/FragmentsAR 

FragmentsAR
.
├── _PapyrusSAR
|   ├── doc
|   ├── Images
|   ├── src
|   ├── target
|   ├── Workspace (Folder includes sub folders contain papyrus fragments)
|   |   ├── 802
|   |   ├── 803_a
|   |   ├── 803_b
|   |   ├── ...
|   |   ├── Fragment_Input.xml (a intial file for input fragments)
|   |   ├── Fragment_Input.xsd
|   |   └── Compounds_12-12-2018_17-24.xml (a intial file for input compounds)
|   ├── ClassDiagram.png
|   ├── ClassDiagram.uls
|   └── pom.xml
├── Readme.txt
└── SetingupPapartonLinux.odt


I. PREREQUISITES
------------------------
- Installation from source requires the following additional packages:
        + Java JDK
        + Eclipse Photon for Java Developer/ Visual Studio Code/ Atom /etc. used for modifying code.
        + Maven
      Command:  sudo apt-get update
                sudo apt-get install maven


II. SETUP
------------------------
- Step 1: Install processing first https://processing.org/download/  .Once it is installed you need to run it once so that it creates all the folders.
- Step 2: Download and install PapARt examples. 
          https://github.com/poqudrof/Papart-examples and then extract to your ketchbook/) which was created by Processing when running it first.
- Step 3: Download and install all other libraries
          PapARt requires additional libraries here is the collection https://www.dropbox.com/s/i8locsl4kmpabtk/libraries.tar.gz?dl=0  
          Copy all libraries to /home/{yourname}/sketchbook/libraries/. 
- Step 4: Install OpenNI from https://www.dropbox.com/s/n5099xtsm71dvof/2-Linux.zip?dl=0 .
          Unzip 2-Linux.zip and then unzip 2-Linux/OpenNI-Linux-x64-2.3. 
- Step 5: Camera test and run calibration
          Print the marker board: You can find it in libraries \PapARt\data\markers\A4-default.pdf or online on github on: https://github.com/poqudrof/PapARt/blob/master/papart/data/markers/A4-default.svg 
          Launch the example Sketchbook -> PapARt-examples -> first-examples -> SAR -> TouchAR. Run the example, and show the printed sheet in front of the camera.
          Launch the calibration Sketchbook -> PapARt-examples -> calibration -> mainCalib. Run the calib, and show the sheet


II. RUN APPLICATION
------------------------
- Step 1: Add SSL working out of the box.
        sudo apt-get install -y ca-certificates-java && update-ca-certificates -f 
- Step 2: Add PapARt library jar to Local Repositories
        mvn install:install-file -Dfile=/home/{yourname}/sketchbook/libraries/PapARt/library/PapARt.jar -DgroupId=fr.inria -DartifactId=papart -Dversion=1.4-SNAPSHOT -Dpackaging=jar
- Step 3: Go to the folder contains source code
        cd {yourpath}/FragmentsAR/PapyrusSAR
- Step 4: Install maven plugins
        mvn -U clean install
- Step 5: Compile the code
        mvn compile
- Step 6: Run the code
        mvn exec:java -Dexec.mainClass="Papyrus"

Note: For next runs, we only run step 3, step 5 and step 6

