const puppeteer = require('puppeteer')

before(async() => {
  browser = await puppeteer.launch({
    args: [
      // Required for Docker version of Puppeteer
      '--no-sandbox',
      '--disable-setuid-sandbox',
      // This will write shared memory files into /tmp instead of /dev/shm,
      // because Docker’s default for /dev/shm is 64MB
      '--disable-dev-shm-usage'
    ]
  })

  const browserVersion = await browser.version()
  console.log(`Started ${browserVersion}`)
})

beforeEach(async() => {
  page = await browser.newPage()
})

afterEach(async() => {
  await page.close()
})

after(async() => {
  await browser.close()
})

describe('App', () => {
  it('renders', async() => {
    const response = await page.goto('http://ui:3000/');
    await expect(page.content()).resolves.toContain('Welcome to Abstract Memery');
  })
})