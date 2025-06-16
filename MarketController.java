@RestController
@RequestMapping("/market")
public class MarketController {

    @Autowired
    private AdService adService;

    @GetMapping
    public Page<AdResponseDto> getAllAds(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "dateDesc") String sortBy) {
        return adService.getAllAds(page, sortBy);
    }

    @GetMapping("/{id}")
    public AdResponseDto getAdById(@PathVariable Long id) {
        return adService.getAdById(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AdResponseDto createAd(@RequestPart("ad") AdRequestDto adRequest,
                                  @RequestPart("file") MultipartFile file,
                                  @RequestParam UUID userId) throws IOException {
        String photoUrl = FileUploadUtil.saveFile(file);
        return adService.createAd(adRequest, photoUrl, userId);
    }
}
