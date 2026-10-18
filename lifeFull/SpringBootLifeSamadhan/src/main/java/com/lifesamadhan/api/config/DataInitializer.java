package com.lifesamadhan.api.config;

import com.lifesamadhan.api.model.*;
import com.lifesamadhan.api.repository.*;
import com.lifesamadhan.api.service.PasswordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final UserRepository userRepository;
    private final PasswordService passwordService;
    private final ProviderTypeRepository providerTypeRepository;
    private final LocationRepository locationRepository;
    private final ServiceCategoryRepository serviceCategoryRepository;
    private final ServiceRepository serviceRepository;
    private final ServiceProviderRepository serviceProviderRepository;
    private final CustomerProfileRepository customerProfileRepository;
    private final ProviderLocationRepository providerLocationRepository;

    public DataInitializer(UserRepository userRepository,
                           PasswordService passwordService,
                           ProviderTypeRepository providerTypeRepository,
                           LocationRepository locationRepository,
                           ServiceCategoryRepository serviceCategoryRepository,
                           ServiceRepository serviceRepository,
                           ServiceProviderRepository serviceProviderRepository,
                           CustomerProfileRepository customerProfileRepository,
                           ProviderLocationRepository providerLocationRepository) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
        this.providerTypeRepository = providerTypeRepository;
        this.locationRepository = locationRepository;
        this.serviceCategoryRepository = serviceCategoryRepository;
        this.serviceRepository = serviceRepository;
        this.serviceProviderRepository = serviceProviderRepository;
        this.customerProfileRepository = customerProfileRepository;
        this.providerLocationRepository = providerLocationRepository;
    }

    @Override
    public void run(String... args) {
        try {
            seedProviderTypes();
            seedLocations();
            seedCategoriesAndServices();
            seedAdminUser();
            seedDemoUsers();
            seedMaharashtrianRecords();
            fixExistingUsersAndProfiles();
            log.info("DataInitializer completed successfully.");
        } catch (Exception e) {
            log.error("DataInitializer failed: {}", e.getMessage(), e);
        }
    }

    private void seedProviderTypes() {
        if (providerTypeRepository.count() == 0) {
            providerTypeRepository.save(ProviderType.builder().name("INDIVIDUAL").description("Individual service technician or freelancer").status("ACTIVE").build());
            providerTypeRepository.save(ProviderType.builder().name("AGENCY").description("Agency with multiple skilled technicians").status("ACTIVE").build());
            providerTypeRepository.save(ProviderType.builder().name("CONTRACTOR").description("Independent licensed contractor").status("ACTIVE").build());
            providerTypeRepository.save(ProviderType.builder().name("COMPANY").description("Registered service enterprise company").status("ACTIVE").build());
            log.info("Seeded provider types.");
        }
    }

    private void seedLocations() {
        if (locationRepository.count() == 0) {
            locationRepository.save(Location.builder().country("India").state("Maharashtra").district("Pune").pincode("411001").status("ACTIVE").build());
            locationRepository.save(Location.builder().country("India").state("Maharashtra").district("Pune").pincode("411014").status("ACTIVE").build());
            locationRepository.save(Location.builder().country("India").state("Maharashtra").district("Pune").pincode("411038").status("ACTIVE").build());
            locationRepository.save(Location.builder().country("India").state("Maharashtra").district("Pune").pincode("411057").status("ACTIVE").build());
            locationRepository.save(Location.builder().country("India").state("Maharashtra").district("Mumbai").pincode("400001").status("ACTIVE").build());
            locationRepository.save(Location.builder().country("India").state("Maharashtra").district("Mumbai").pincode("400050").status("ACTIVE").build());
            locationRepository.save(Location.builder().country("India").state("Maharashtra").district("Mumbai").pincode("400053").status("ACTIVE").build());
            locationRepository.save(Location.builder().country("India").state("Maharashtra").district("Thane").pincode("400601").status("ACTIVE").build());
            locationRepository.save(Location.builder().country("India").state("Maharashtra").district("Navi Mumbai").pincode("400703").status("ACTIVE").build());
            locationRepository.save(Location.builder().country("India").state("Maharashtra").district("Nagpur").pincode("440001").status("ACTIVE").build());
            locationRepository.save(Location.builder().country("India").state("Maharashtra").district("Nashik").pincode("422001").status("ACTIVE").build());
            log.info("Seeded default locations.");
        }
    }

    private Location getOrCreateLocation(String country, String state, String district, String pincode) {
        return locationRepository.findAll().stream()
                .filter(l -> l.getState().equalsIgnoreCase(state) &&
                             l.getDistrict().equalsIgnoreCase(district) &&
                             l.getPincode().equals(pincode))
                .findFirst()
                .orElseGet(() -> locationRepository.save(Location.builder()
                        .country(country)
                        .state(state)
                        .district(district)
                        .pincode(pincode)
                        .status("ACTIVE")
                        .build()));
    }

    private ServiceCategory getCategoryByName(String name) {
        return serviceCategoryRepository.findAll().stream()
                .filter(c -> c.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseGet(() -> serviceCategoryRepository.save(ServiceCategory.builder().name(name).status("ACTIVE").build()));
    }

    private void seedCategoriesAndServices() {
        if (serviceCategoryRepository.count() == 0) {
            ServiceCategory plumbing = serviceCategoryRepository.save(ServiceCategory.builder().name("Plumbing").status("ACTIVE").build());
            ServiceCategory electrical = serviceCategoryRepository.save(ServiceCategory.builder().name("Electrical").status("ACTIVE").build());
            ServiceCategory carpentry = serviceCategoryRepository.save(ServiceCategory.builder().name("Carpentry").status("ACTIVE").build());
            ServiceCategory painting = serviceCategoryRepository.save(ServiceCategory.builder().name("Painting").status("ACTIVE").build());
            ServiceCategory cleaning = serviceCategoryRepository.save(ServiceCategory.builder().name("Home Cleaning").status("ACTIVE").build());
            ServiceCategory appliance = serviceCategoryRepository.save(ServiceCategory.builder().name("Appliance Repair").status("ACTIVE").build());
            ServiceCategory ac = serviceCategoryRepository.save(ServiceCategory.builder().name("AC Services").status("ACTIVE").build());
            ServiceCategory pest = serviceCategoryRepository.save(ServiceCategory.builder().name("Pest Control").status("ACTIVE").build());

            if (serviceRepository.count() == 0) {
                // Plumbing
                serviceRepository.save(Service.builder().categoryId(plumbing.getId()).name("Tap & Pipe Leak Repair").status("ACTIVE").build());
                serviceRepository.save(Service.builder().categoryId(plumbing.getId()).name("Water Tank Cleaning").status("ACTIVE").build());
                serviceRepository.save(Service.builder().categoryId(plumbing.getId()).name("Toilet & Basin Fitting").status("ACTIVE").build());

                // Electrical
                serviceRepository.save(Service.builder().categoryId(electrical.getId()).name("Fan & Lighting Repair").status("ACTIVE").build());
                serviceRepository.save(Service.builder().categoryId(electrical.getId()).name("Switchboard & Wiring").status("ACTIVE").build());
                serviceRepository.save(Service.builder().categoryId(electrical.getId()).name("Inverter & Fuse Repair").status("ACTIVE").build());

                // Carpentry
                serviceRepository.save(Service.builder().categoryId(carpentry.getId()).name("Furniture Assembly").status("ACTIVE").build());
                serviceRepository.save(Service.builder().categoryId(carpentry.getId()).name("Door & Lock Repair").status("ACTIVE").build());

                // Painting
                serviceRepository.save(Service.builder().categoryId(painting.getId()).name("Full Home Painting").status("ACTIVE").build());
                serviceRepository.save(Service.builder().categoryId(painting.getId()).name("Wall Waterproofing").status("ACTIVE").build());

                // Cleaning
                serviceRepository.save(Service.builder().categoryId(cleaning.getId()).name("Deep Home Cleaning").status("ACTIVE").build());
                serviceRepository.save(Service.builder().categoryId(cleaning.getId()).name("Kitchen & Bathroom Cleaning").status("ACTIVE").build());

                // Appliance
                serviceRepository.save(Service.builder().categoryId(appliance.getId()).name("Washing Machine Repair").status("ACTIVE").build());
                serviceRepository.save(Service.builder().categoryId(appliance.getId()).name("Refrigerator Repair").status("ACTIVE").build());

                // AC
                serviceRepository.save(Service.builder().categoryId(ac.getId()).name("AC Regular Servicing").status("ACTIVE").build());
                serviceRepository.save(Service.builder().categoryId(ac.getId()).name("AC Gas Refill & Jet Wash").status("ACTIVE").build());

                // Pest Control
                serviceRepository.save(Service.builder().categoryId(pest.getId()).name("Cockroach & Termite Control").status("ACTIVE").build());
            }
            log.info("Seeded service categories and services.");
        }
    }

    private void seedAdminUser() {
        String adminEmail = "admin@mail.com";
        Optional<User> existingUser = userRepository.findByEmail(adminEmail);

        if (existingUser.isEmpty()) {
            User admin = User.builder()
                    .name("Admin")
                    .email(adminEmail)
                    .mobile("9999999999")
                    .password(passwordService.hash("Admin@LifeSamadan123"))
                    .role("ADMIN")
                    .status("ACTIVE")
                    .build();

            userRepository.save(admin);
            log.info("Default Admin user created successfully: {}", adminEmail);
        } else {
            User admin = existingUser.get();
            admin.setPassword(passwordService.hash("Admin@LifeSamadan123"));
            admin.setRole("ADMIN");
            admin.setStatus("ACTIVE");
            userRepository.save(admin);
            log.info("Default Admin user already exists. Password and status refreshed: {}", adminEmail);
        }
    }

    private void seedDemoUsers() {
        try {
            Long defaultLocationId = locationRepository.findAll().stream().findFirst().map(Location::getId).orElse(1L);
            Long defaultCategoryId = serviceCategoryRepository.findAll().stream().findFirst().map(ServiceCategory::getId).orElse(1L);

            // Configure testcustomer@mail.com
            Optional<User> custOpt = userRepository.findByEmail("testcustomer@mail.com");
            if (custOpt.isPresent()) {
                User cust = custOpt.get();
                cust.setPassword(passwordService.hash("Customer@123"));
                cust.setStatus("ACTIVE");
                userRepository.save(cust);

                if (customerProfileRepository.findByUserId(cust.getId()).isEmpty()) {
                    customerProfileRepository.save(CustomerProfile.builder()
                            .userId(cust.getId())
                            .locationId(defaultLocationId)
                            .address("Flat 402, Shivajinagar, Pune")
                            .build());
                }
                log.info("testcustomer@mail.com initialized with password 'Customer@123'");
            }

            // Configure testprovider@mail.com
            Optional<User> provOpt = userRepository.findByEmail("testprovider@mail.com");
            if (provOpt.isPresent()) {
                User prov = provOpt.get();
                prov.setPassword(passwordService.hash("Provider@123"));
                prov.setStatus("ACTIVE");
                userRepository.save(prov);

                ServiceProvider sp = serviceProviderRepository.findByUserId(prov.getId())
                        .orElseGet(() -> serviceProviderRepository.save(ServiceProvider.builder()
                                .userId(prov.getId())
                                .verified(true)
                                .availability("AVAILABLE")
                                .providerType("INDIVIDUAL")
                                .hourlyRate(350.0)
                                .serviceCategoryId(defaultCategoryId)
                                .completedJobsCount(5)
                                .rejectedJobsCount(0)
                                .build()));

                if (providerLocationRepository.findByProviderId(sp.getId()).isEmpty()) {
                    providerLocationRepository.save(ProviderLocation.builder()
                            .providerId(sp.getId())
                            .locationId(defaultLocationId)
                            .status("APPROVED")
                            .build());
                }
                log.info("testprovider@mail.com initialized with password 'Provider@123'");
            }
        } catch (Exception e) {
            log.error("Failed to seed demo users: {}", e.getMessage(), e);
        }
    }

    private void seedMaharashtrianRecords() {
        try {
            log.info("Seeding Maharashtrian locations and people records...");

            // 1. Mumbai Locations (Dadar, Bandra, Andheri, Borivali, Ghatkopar, Malad, Parel, Fort)
            Location mumFort = getOrCreateLocation("India", "Maharashtra", "Mumbai", "400001");
            Location mumBandra = getOrCreateLocation("India", "Maharashtra", "Mumbai", "400050");
            Location mumAndheri = getOrCreateLocation("India", "Maharashtra", "Mumbai", "400053");
            Location mumDadar = getOrCreateLocation("India", "Maharashtra", "Mumbai", "400028");
            Location mumBorivali = getOrCreateLocation("India", "Maharashtra", "Mumbai", "400092");
            Location mumGhatkopar = getOrCreateLocation("India", "Maharashtra", "Mumbai", "400077");
            Location mumMalad = getOrCreateLocation("India", "Maharashtra", "Mumbai", "400064");
            Location mumParel = getOrCreateLocation("India", "Maharashtra", "Mumbai", "400012");

            // 2. Navi Mumbai Locations (Vashi, Nerul, Belapur, Kharghar, Panvel, Airoli, Kopar Khairane, Sanpada)
            Location nmVashi = getOrCreateLocation("India", "Maharashtra", "Navi Mumbai", "400703");
            Location nmNerul = getOrCreateLocation("India", "Maharashtra", "Navi Mumbai", "400706");
            Location nmBelapur = getOrCreateLocation("India", "Maharashtra", "Navi Mumbai", "400614");
            Location nmKharghar = getOrCreateLocation("India", "Maharashtra", "Navi Mumbai", "410210");
            Location nmPanvel = getOrCreateLocation("India", "Maharashtra", "Navi Mumbai", "410206");
            Location nmAiroli = getOrCreateLocation("India", "Maharashtra", "Navi Mumbai", "400708");
            Location nmKoparKhairane = getOrCreateLocation("India", "Maharashtra", "Navi Mumbai", "400709");
            Location nmSanpada = getOrCreateLocation("India", "Maharashtra", "Navi Mumbai", "400705");

            // 3. Maharashtrian Service Providers
            seedProvider(
                    "Sachin Deshmukh",
                    "sachin.deshmukh@mail.com",
                    "9820011001",
                    "Plumbing",
                    "INDIVIDUAL",
                    350.0,
                    28,
                    Arrays.asList(mumDadar, mumBandra, mumAndheri)
            );

            seedProvider(
                    "Ganesh Pawar",
                    "ganesh.pawar@mail.com",
                    "9820011002",
                    "Electrical",
                    "INDIVIDUAL",
                    400.0,
                    34,
                    Arrays.asList(nmVashi, nmNerul, nmSanpada)
            );

            seedProvider(
                    "Santosh Shinde",
                    "santosh.shinde@mail.com",
                    "9820011003",
                    "Carpentry",
                    "CONTRACTOR",
                    450.0,
                    22,
                    Arrays.asList(mumAndheri, mumBorivali, mumMalad)
            );

            seedProvider(
                    "Nitin Patil",
                    "nitin.patil@mail.com",
                    "9820011004",
                    "AC Services",
                    "AGENCY",
                    550.0,
                    45,
                    Arrays.asList(nmVashi, nmNerul, nmBelapur, nmKharghar)
            );

            seedProvider(
                    "Rohan Kadam",
                    "rohan.kadam@mail.com",
                    "9820011005",
                    "Painting",
                    "CONTRACTOR",
                    500.0,
                    19,
                    Arrays.asList(mumDadar, mumParel, mumBandra)
            );

            seedProvider(
                    "Swapnil Gaikwad",
                    "swapnil.gaikwad@mail.com",
                    "9820011006",
                    "Home Cleaning",
                    "COMPANY",
                    600.0,
                    52,
                    Arrays.asList(nmKharghar, nmPanvel, nmBelapur)
            );

            seedProvider(
                    "Mahesh Jadhav",
                    "mahesh.jadhav@mail.com",
                    "9820011007",
                    "Appliance Repair",
                    "INDIVIDUAL",
                    420.0,
                    30,
                    Arrays.asList(mumGhatkopar, mumDadar, mumFort)
            );

            seedProvider(
                    "Amol More",
                    "amol.more@mail.com",
                    "9820011008",
                    "Pest Control",
                    "AGENCY",
                    450.0,
                    38,
                    Arrays.asList(nmAiroli, nmKoparKhairane, nmVashi)
            );

            // 4. Maharashtrian Customers
            seedCustomer(
                    "Aniket Kulkarni",
                    "aniket.kulkarni@mail.com",
                    "9819022001",
                    "B-204, Shreeram Niwas, Ranade Road, Dadar West, Mumbai",
                    mumDadar
            );

            seedCustomer(
                    "Pooja Salunkhe",
                    "pooja.salunkhe@mail.com",
                    "9819022002",
                    "Flat 501, Sea View Residency, Sector 17, Vashi, Navi Mumbai",
                    nmVashi
            );

            seedCustomer(
                    "Vikram Joshi",
                    "vikram.joshi@mail.com",
                    "9819022003",
                    "A-12, Green Acres, Lokhandwala Complex, Andheri West, Mumbai",
                    mumAndheri
            );

            seedCustomer(
                    "Snehal Sawant",
                    "snehal.sawant@mail.com",
                    "9819022004",
                    "Flat 102, Palm Beach Towers, Sector 28, Nerul, Navi Mumbai",
                    nmNerul
            );

            seedCustomer(
                    "Rahul Bhosale",
                    "rahul.bhosale@mail.com",
                    "9819022005",
                    "Row House No. 4, Hiranandani Estate, Sector 7, Kharghar, Navi Mumbai",
                    nmKharghar
            );

            seedCustomer(
                    "Tanvi Chavan",
                    "tanvi.chavan@mail.com",
                    "9819022006",
                    "Flat 803, Hill View Heights, Shimpoli Road, Borivali West, Mumbai",
                    mumBorivali
            );

            log.info("Maharashtrian locations, service providers, and customer records seeded successfully.");
        } catch (Exception e) {
            log.error("Failed to seed Maharashtrian records: {}", e.getMessage(), e);
        }
    }

    private void seedProvider(String name, String email, String mobile, String categoryName,
                              String providerType, Double hourlyRate, Integer completedJobs,
                              List<Location> locations) {
        try {
            Optional<User> userOpt = userRepository.findByEmail(email);
            User user;
            if (userOpt.isEmpty()) {
                user = userRepository.save(User.builder()
                        .name(name)
                        .email(email)
                        .mobile(mobile)
                        .password(passwordService.hash("Provider@123"))
                        .role("SERVICEPROVIDER")
                        .status("ACTIVE")
                        .build());
            } else {
                user = userOpt.get();
                user.setName(name);
                user.setPassword(passwordService.hash("Provider@123"));
                user.setStatus("ACTIVE");
                userRepository.save(user);
            }

            ServiceCategory cat = getCategoryByName(categoryName);

            ServiceProvider sp = serviceProviderRepository.findByUserId(user.getId())
                    .orElseGet(() -> serviceProviderRepository.save(ServiceProvider.builder()
                            .userId(user.getId())
                            .verified(true)
                            .availability("AVAILABLE")
                            .providerType(providerType)
                            .hourlyRate(hourlyRate)
                            .serviceCategoryId(cat.getId())
                            .completedJobsCount(completedJobs)
                            .rejectedJobsCount(0)
                            .build()));

            sp.setVerified(true);
            sp.setAvailability("AVAILABLE");
            sp.setProviderType(providerType);
            sp.setHourlyRate(hourlyRate);
            sp.setServiceCategoryId(cat.getId());
            sp.setCompletedJobsCount(completedJobs);
            serviceProviderRepository.save(sp);

            if (locations != null) {
                for (Location loc : locations) {
                    Long provId = sp.getId();
                    Long locId = loc.getId();
                    boolean exists = providerLocationRepository.findAll().stream()
                            .anyMatch(pl -> pl.getProviderId().equals(provId) && pl.getLocationId().equals(locId));
                    if (!exists) {
                        providerLocationRepository.save(ProviderLocation.builder()
                                .providerId(provId)
                                .locationId(locId)
                                .status("APPROVED")
                                .build());
                    }
                }
            }
            log.info("Seeded provider {} ({}) under category {}", name, email, categoryName);
        } catch (Exception e) {
            log.error("Failed to seed provider {}: {}", name, e.getMessage(), e);
        }
    }

    private void seedCustomer(String name, String email, String mobile, String address, Location location) {
        try {
            Optional<User> userOpt = userRepository.findByEmail(email);
            User user;
            if (userOpt.isEmpty()) {
                user = userRepository.save(User.builder()
                        .name(name)
                        .email(email)
                        .mobile(mobile)
                        .password(passwordService.hash("Customer@123"))
                        .role("CUSTOMER")
                        .status("ACTIVE")
                        .build());
            } else {
                user = userOpt.get();
                user.setName(name);
                user.setPassword(passwordService.hash("Customer@123"));
                user.setStatus("ACTIVE");
                userRepository.save(user);
            }

            CustomerProfile profile = customerProfileRepository.findByUserId(user.getId())
                    .orElseGet(() -> CustomerProfile.builder().userId(user.getId()).build());

            profile.setAddress(address);
            if (location != null) {
                profile.setLocationId(location.getId());
            }
            customerProfileRepository.save(profile);
            log.info("Seeded customer {} ({}) with address in {}", name, email, location != null ? location.getDistrict() : "N/A");
        } catch (Exception e) {
            log.error("Failed to seed customer {}: {}", name, e.getMessage(), e);
        }
    }

    private void fixExistingUsersAndProfiles() {
        try {
            Long defaultLocationId = locationRepository.findAll().stream().findFirst().map(Location::getId).orElse(1L);
            Long defaultCategoryId = serviceCategoryRepository.findAll().stream().findFirst().map(ServiceCategory::getId).orElse(1L);

            // Fix any customer profiles with null or 0 locationId
            List<CustomerProfile> profiles = customerProfileRepository.findAll();
            for (CustomerProfile p : profiles) {
                if (p.getLocationId() == null || p.getLocationId() <= 0) {
                    p.setLocationId(defaultLocationId);
                    customerProfileRepository.save(p);
                    log.info("Updated customer profile ID {} with location ID {}", p.getId(), defaultLocationId);
                }
            }

            // Fix any SERVICEPROVIDER users without a ServiceProvider record
            List<User> spUsers = userRepository.findAll().stream()
                    .filter(u -> "SERVICEPROVIDER".equalsIgnoreCase(u.getRole()))
                    .toList();

            for (User u : spUsers) {
                if (serviceProviderRepository.findByUserId(u.getId()).isEmpty()) {
                    ServiceProvider sp = serviceProviderRepository.save(ServiceProvider.builder()
                            .userId(u.getId())
                            .verified(true)
                            .availability("AVAILABLE")
                            .providerType("INDIVIDUAL")
                            .hourlyRate(350.0)
                            .serviceCategoryId(defaultCategoryId)
                            .completedJobsCount(0)
                            .rejectedJobsCount(0)
                            .build());

                    providerLocationRepository.save(ProviderLocation.builder()
                            .providerId(sp.getId())
                            .locationId(defaultLocationId)
                            .status("APPROVED")
                            .build());
                    log.info("Created missing ServiceProvider record for user ID {} ({})", u.getId(), u.getEmail());
                }
            }
        } catch (Exception e) {
            log.error("Failed to fix existing users and profiles: {}", e.getMessage(), e);
        }
    }
}
