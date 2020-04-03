import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('renders page title', () => {
  const { getByText } = render(<App />);
  const headerElement = getByText(/create your meme/i);
  expect(headerElement).toBeInTheDocument();
});
