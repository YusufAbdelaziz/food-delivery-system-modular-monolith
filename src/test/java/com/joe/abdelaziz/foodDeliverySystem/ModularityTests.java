package com.joe.abdelaziz.foodDeliverySystem;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

class ModularityTests {

  @Test
  void verifyModularity() {
    // Creates a model of your app based on package structure
    ApplicationModules modules = ApplicationModules.of(FoodDeliverySystemApplication.class);

    // Fails the build if any module accesses another module's internal classes
    // or if there are cyclic dependencies
    // modules.forEach(System.out::println);
    modules.verify();
    System.out.println(modules.detectViolations());

  }

  @Test
  void createModuleDocumentation() {
    ApplicationModules modules = ApplicationModules.of(FoodDeliverySystemApplication.class);
    new Documenter(modules)
        .writeDocumentation()
        .writeIndividualModulesAsPlantUml()
        .writeModuleCanvases();
  }
}