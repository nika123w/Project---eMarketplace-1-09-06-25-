@Service
public class AdService {
    @Autowired
    private AdRepository adRepository;

    @Autowired
    private UserRepository userRepository;

    public Page<AdResponseDto> getAllAds(int page, String sortBy) {
        Sort sort;
        switch (sortBy) {
            case "dateAsc":
                sort = Sort.by("submissionTime").ascending();
                break;
            case "priceAsc":
                sort = Sort.by("price").ascending();
                break;
            case "priceDesc":
                sort = Sort.by("price").descending();
                break;
            default:
                sort = Sort.by("submissionTime").descending();
                break;
        }

        Pageable pageable = PageRequest.of(page, 6, sort);
        return adRepository.findAll(pageable).map(this::toDto);
    }

    public AdResponseDto getAdById(Long id) {
        return adRepository.findById(id).map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Ad not found"));
    }

    public AdResponseDto createAd(AdRequestDto dto, String photoUrl, UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        Ad ad = new Ad();
        ad.setName(dto.getName());
        ad.setPrice(dto.getPrice());
        ad.setDescription(dto.getDescription());
        ad.setSubmissionTime(LocalDateTime.now());
        ad.setPhotoUrl(photoUrl);
        ad.setUser(user);

        return toDto(adRepository.save(ad));
    }

    private AdResponseDto toDto(Ad ad) {
        AdResponseDto dto = new AdResponseDto();
        dto.setId(ad.getId());
        dto.setName(ad.getName());
        dto.setPrice(ad.getPrice());
        dto.setDescription(ad.getDescription());
        dto.setSubmissionTime(ad.getSubmissionTime());
        dto.setPhotoUrl(ad.getPhotoUrl());
        return dto;
    }
}
