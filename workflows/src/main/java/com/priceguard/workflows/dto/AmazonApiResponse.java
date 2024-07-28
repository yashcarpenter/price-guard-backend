package com.priceguard.workflows.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmazonApiResponse {
    private String status;

    @JsonProperty("request_id")
    private String requestId;

    private Parameters parameters;
    private Data data;

    // Getters and Setters

    @lombok.Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Parameters {
        private String asin;
        private String country;

        // Getters and Setters
    }

    @lombok.Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {
        private String asin;

        @JsonProperty("product_title")
        private String productTitle;

        @JsonProperty("product_price")
        private String productPrice;

        @JsonProperty("product_original_price")
        private String productOriginalPrice;

        private String currency;
        private String country;

        @JsonProperty("product_star_rating")
        private String productStarRating;

        @JsonProperty("product_num_ratings")
        private int productNumRatings;

        @JsonProperty("product_url")
        private String productUrl;

        @JsonProperty("product_photo")
        private String productPhoto;

        @JsonProperty("product_num_offers")
        private int productNumOffers;

        @JsonProperty("product_availability")
        private String productAvailability;

        @JsonProperty("is_best_seller")
        private boolean isBestSeller;

        @JsonProperty("is_amazon_choice")
        private boolean isAmazonChoice;

        @JsonProperty("is_prime")
        private boolean isPrime;

        @JsonProperty("climate_pledge_friendly")
        private boolean climatePledgeFriendly;

        @JsonProperty("sales_volume")
        private String salesVolume;

        @JsonProperty("about_product")
        private String[] aboutProduct;

        @JsonProperty("product_description")
        private Object productDescription;

        @JsonProperty("product_information")
        private ProductInformation productInformation;

        @JsonProperty("product_photos")
        private String[] productPhotos;

        @JsonProperty("product_details")
        private ProductDetails productDetails;

        @JsonProperty("customers_say")
        private String customersSay;

        @JsonProperty("category_path")
        private CategoryPath[] categoryPath;

        @JsonProperty("product_variations")
        private ProductVariations productVariations;

        // Getters and Setters

        @lombok.Data
        @AllArgsConstructor
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class ProductInformation {
            private String brand;
            private String model;
            private String energyEfficiency;
            private String capacity;

            @JsonProperty("annual_energy_consumption")
            private String annualEnergyConsumption;

            @JsonProperty("noise_level")
            private String noiseLevel;

            @JsonProperty("installation_type")
            private String installationType;

            @JsonProperty("part_number")
            private String partNumber;

            @JsonProperty("form_factor")
            private String formFactor;

            @JsonProperty("special_features")
            private String specialFeatures;

            private String colour;

            @JsonProperty("control_console")
            private String controlConsole;

            private String voltage;
            private String wattage;
            private String certification;
            private String material;

            @JsonProperty("included_components")
            private String includedComponents;

            @JsonProperty("batteries_included")
            private String batteriesIncluded;

            @JsonProperty("batteries_required")
            private String batteriesRequired;

            @JsonProperty("battery_cell_type")
            private String batteryCellType;

            private String manufacturer;

            @JsonProperty("country_of_origin")
            private String countryOfOrigin;

            private String asin;

            @JsonProperty("best_sellers_rank")
            private String bestSellersRank;

            @JsonProperty("date_first_available")
            private String dateFirstAvailable;

            private String packer;

            @JsonProperty("item_weight")
            private String itemWeight;

            @JsonProperty("item_dimensions_lxwxh")
            private String itemDimensionsLxWxH;

            @JsonProperty("net_quantity")
            private String netQuantity;

            @JsonProperty("generic_name")
            private String genericName;

            // Getters and Setters
        }

        @lombok.Data
        @AllArgsConstructor
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class ProductDetails {
            private String brand;
            private String capacity;

            @JsonProperty("cooling_power")
            private String coolingPower;

            @JsonProperty("special_feature")
            private String specialFeature;

            @JsonProperty("product_dimensions")
            private String productDimensions;

            // Getters and Setters
        }

        @lombok.Data
        @AllArgsConstructor
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class CategoryPath {
            private String id;
            private String name;
            private String link;

            // Getters and Setters
        }

        @lombok.Data
        @AllArgsConstructor
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class ProductVariations {
            private Style[] style;

            // Getters and Setters

            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Style {
                private String asin;
                private String value;

                @JsonProperty("is_available")
                private boolean isAvailable;

            }
        }
    }
}
