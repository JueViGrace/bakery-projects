import { useMediaQuery } from 'react-responsive';

export default function NavButton({ className }) {
  const isSmallScreen = useMediaQuery({ query: 'max-width: 768px' });

  return (
    <button className={className}>
      {isSmallScreen ? <h1>hola</h1> : <h1>adios</h1>}
    </button>
  );
}
