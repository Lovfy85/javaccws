package ui.tests;

import model.*;

import repository.UserRepository;


public class UserRepositoryTest {

    public static void main(String[] args) {


        UserRepository userRepository =
                new UserRepository();


        try {


            System.out.println(
                    "=== USER REPOSITORY TEST ==="
            );


            User user =
                    new User(
                            "USER002",
                            "testuser2",
                            "hashed_password",
                            "Test User Two",
                            new StylesProfile(
                                    "CASUAL",
                                    "Warm"
                            ),
                            new Wardrobe()
                    );



            System.out.println(
                    "\nChecking if user already exists..."
            );


            if(userRepository.existsByUsername("testuser2")) {

                System.out.println(
                        "User already exists. Skipping save."
                );

            }

            else {


                System.out.println(
                        "\nSaving user..."
                );


                userRepository.save(user);


                System.out.println(
                        "User saved successfully!"
                );

            }




            System.out.println(
                    "\nSearching by username..."
            );


            User foundUser =
                    userRepository.findByUsername(
                            "testuser2"
                    );



            if(foundUser != null) {


                System.out.println(
                        "User found!"
                );


                foundUser.display();

            }

            else {


                System.out.println(
                        "User not found."
                );

            }




            System.out.println(
                    "\nSearching by ID..."
            );


            User foundById =
                    userRepository.findById(
                            "USER002"
                    );



            if(foundById != null) {


                System.out.println(
                        "User found by ID!"
                );


                foundById.display();

            }

            else {


                System.out.println(
                        "User not found."
                );

            }




            System.out.println(
                    "\nChecking username existence..."
            );


            boolean exists =
                    userRepository.existsByUsername(
                            "testuser2"
                    );


            System.out.println(
                    "Username exists: " + exists
            );



            System.out.println(
                    "\n=== TEST COMPLETE ==="
            );


        }
        catch(Exception e) {


            e.printStackTrace();

        }

    }

}